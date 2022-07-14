package com.tosmart.xiexuming.libtsparser.tspacket;

import java.io.FileInputStream;
import java.util.Observable;


/**
 * @author xiexuming
 */
public class TsPacketManager extends Observable {
    private static final int READ_BYTES = 2048;
    private static final int TS_PACKAGE_LENGTH = 188;
    private static final int TS_PACKAGE_SUFFIX_LENGTH = 204;
    private static final int TS_PACKET_HEAD_PREFIX = 0x47;
    private static final int TS_PACKAGE_ENOUGH_TIME = 10;
    private static final int MIN_CUT_PACKET_NUMBER = 2040;

    private static volatile TsPacketManager instance;

    private long mPacketStarPosition;
    private int mPacketLength;


    private TsPacketManager() {
    }

    public static TsPacketManager getInstance() {
        if (instance == null) {
            synchronized (TsPacketManager.class) {
                instance = new TsPacketManager();
            }
        }
        return instance;
    }

    private boolean checkNextNineTsPackage(byte[] checkByteArray) {
        int tsPackageTime = 1;
        int checkIndex = 0;
        for (int i = 1; i < TS_PACKAGE_ENOUGH_TIME; i++) {
            if (checkByteArray[checkIndex + TS_PACKAGE_LENGTH] == TS_PACKET_HEAD_PREFIX) {
                tsPackageTime++;
                checkIndex = checkIndex + TS_PACKAGE_LENGTH;
            } else {
                break;
            }
            if (tsPackageTime == TS_PACKAGE_ENOUGH_TIME) {
                mPacketLength = TS_PACKAGE_LENGTH;
                return true;
            }
        }
        checkIndex = 0;
        tsPackageTime = 1;
        for (int i = 1; i < TS_PACKAGE_ENOUGH_TIME; i++) {
            if (checkByteArray[checkIndex + TS_PACKAGE_SUFFIX_LENGTH] == TS_PACKET_HEAD_PREFIX) {
                tsPackageTime++;
                checkIndex = checkIndex + TS_PACKAGE_SUFFIX_LENGTH;
            } else {
                break;
            }
            if (tsPackageTime >= TS_PACKAGE_ENOUGH_TIME) {
                mPacketLength = TS_PACKAGE_SUFFIX_LENGTH;
                return true;
            }

        }
        return false;
    }

    private byte[] cutPacket(int headPrefixIndex, byte[] firstByteArray, byte[] nextByteArray) {
        byte[] checkByteArray = new byte[READ_BYTES];
        if ((READ_BYTES - headPrefixIndex) < MIN_CUT_PACKET_NUMBER) {
            System.arraycopy(firstByteArray, headPrefixIndex, checkByteArray, 0, READ_BYTES - headPrefixIndex);
            System.arraycopy(nextByteArray, 0, checkByteArray, READ_BYTES - headPrefixIndex, headPrefixIndex);
        } else {
            checkByteArray = firstByteArray;
        }
        return checkByteArray;
    }

    private boolean checkTsLength(byte[] firstByteArray, byte[] nextByteArray) {
        boolean findPacketLength;
        for (int i = 0; i < READ_BYTES; i++) {
            if (firstByteArray[i] == TS_PACKET_HEAD_PREFIX) {
                byte[] checkByteArray = cutPacket(i, firstByteArray, nextByteArray);
                findPacketLength = checkNextNineTsPackage(checkByteArray);
                if (findPacketLength) {
                    mPacketStarPosition = mPacketStarPosition + i;
                    return true;
                }
            }
        }
        return false;
    }

    public int getTsPacketLength(String filePath) {
        boolean findTsPacketLength = false;
        mPacketLength = -1;
        int len;
        byte[] firstByteArray = new byte[READ_BYTES];
        byte[] nextByteArray = new byte[READ_BYTES];
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            len = fileInputStream.read(firstByteArray);
            while ((len != -1) && (!findTsPacketLength)) {
                len = fileInputStream.read(nextByteArray);
                findTsPacketLength = checkTsLength(firstByteArray, nextByteArray);
                if (!findTsPacketLength) {
                    mPacketStarPosition = mPacketStarPosition + firstByteArray.length;
                    System.arraycopy(nextByteArray, 0, firstByteArray, 0, READ_BYTES);
                }
            }
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mPacketLength;
    }

    public void distributePacket(String filePath) {
        if (mPacketLength <= 0) {
            return;
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            if (mPacketStarPosition > 0) {
                long skip = fileInputStream.skip(mPacketStarPosition);
                System.out.println("file skip: " + skip);
            }

            byte[] packetBuf = new byte[mPacketLength * 10];
            byte[] packetData = new byte[mPacketLength];
            while (fileInputStream.read(packetBuf) != -1) {
                for (int i = 0; i < packetBuf.length; ) {
                    TsPacket tsPacket = new TsPacket();
                    System.arraycopy(packetBuf, i, packetData, 0, mPacketLength);
                    tsPacket.setObjectByByte(packetData);

                    this.setChanged();
                    this.notifyObservers(tsPacket);

                    i = i + mPacketLength;
                }

                if (countObservers() <= 0) {
                    break;
                }
            }

            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


