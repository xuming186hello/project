package com.tosmart.xiexuming.libtsparser.tspacket;

import androidx.annotation.NonNull;

import java.util.Arrays;

/**
 * @author xiexuming
 */
public class TsPacket {
    public static final byte CHECK_RIGHT_BIT = 0x01;
    public static final byte CHECK_RIGHT_TWO_BITS = 0x03;
    public static final byte CHECK_RIGHT_EIGHT = 0x0F;
    public static final byte REMOVE_LEFT_THREE_BIT = 0x1F;

    private int pid;
    private int transportScramblingControl;
    private int transportErrorIndicator;
    private int payloadUnitStartIndicator;
    private int priority;
    private int adaptationFieldControl;
    private int continuityCounter;
    private byte[] payload;


    public byte[] getPayload() {
        return payload;
    }

    @NonNull
    @Override
    public String toString() {
        return "TsPacket{" +
                "pid=" + pid +
                ", transportScramblingControl=" + transportScramblingControl +
                ", transportErrorIndicator=" + transportErrorIndicator +
                ", payloadUnitStartIndicator=" + payloadUnitStartIndicator +
                ", priority=" + priority +
                ", adaptationFieldControl=" + adaptationFieldControl +
                ", continuityCounter=" + continuityCounter +
                ", payload=" + Arrays.toString(payload) +
                '}';
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public void setObjectByByte(byte[] tsPacket) {
        int pid = (tsPacket[1] & REMOVE_LEFT_THREE_BIT) << 8 | (tsPacket[2] & 0XFF);
        this.setTransportErrorIndicator(tsPacket[1] >> 7);
        this.setPayloadUnitStartIndicator((tsPacket[1] >> 6 & CHECK_RIGHT_BIT));
        this.setPriority(tsPacket[1] >> 5 & CHECK_RIGHT_BIT);
        this.setPid(pid);
        this.setTransportScramblingControl(((tsPacket[3] >> 6) & CHECK_RIGHT_TWO_BITS));
        this.setAdaptationFieldControl(tsPacket[3] >> 4 & CHECK_RIGHT_TWO_BITS);
        this.setContinuityCounter(tsPacket[3] & CHECK_RIGHT_EIGHT);
        this.setPayload(tsPacket);
    }


    public int getPid() {
        return pid;
    }

    public int getTransportScramblingControl() {
        return transportScramblingControl;
    }

    public int getTransportErrorIndicator() {
        return transportErrorIndicator;
    }

    public void setTransportErrorIndicator(int transportErrorIndicator) {
        this.transportErrorIndicator = transportErrorIndicator;
    }

    public int getPayloadUnitStartIndicator() {
        return payloadUnitStartIndicator;
    }

    public void setPayloadUnitStartIndicator(int payloadUnitStartIndicator) {
        this.payloadUnitStartIndicator = payloadUnitStartIndicator;
    }

    public int getPriority() {
        return priority;
    }

    public int getAdaptationFieldControl() {
        return adaptationFieldControl;
    }

    public void setAdaptationFieldControl(int adaptationFieldControl) {
        this.adaptationFieldControl = adaptationFieldControl;
    }

    public int getContinuityCounter() {
        return continuityCounter;
    }

    public void setTransportScramblingControl(int transportScramblingControl) {
        this.transportScramblingControl = transportScramblingControl;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setContinuityCounter(int continuityCounter) {
        this.continuityCounter = continuityCounter;
    }

    public void setPayload(byte[] data) {
        byte[] payload = new byte[data.length - 4];
        System.arraycopy(data, 4, payload, 0, payload.length);
        this.payload = payload;
    }
}
