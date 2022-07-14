package com.tosmart.xiexuming.libtsparser.section;

import com.tosmart.xiexuming.libtsparser.tspacket.TsPacket;

import java.util.HashMap;

/**
 * @author xiexuming
 */
public abstract class SectionManager {
    private static final int SECTION_HEAD = 3;
    private static final int MOD_COUNTER_LENGTH = 16;
    private static final int PAYLOAD_VALID_LENGTH = 184;
    private static final int POINT_FIELD_LENGTH = 1;

    public SectionListener mSectionListener;

    private Section mSection;
    private final HashMap<Integer, Section> mPidSections = new HashMap<>();
    private final HashMap<Integer, Integer> mPreCounter = new HashMap<>();

    /**
     * 组成特定表
     *
     * @param commonSection 一般section
     * @param pid 包的pid
     */
    public abstract void composeSpecifiedSection(Section commonSection, Integer pid);

    public void getSection(TsPacket tsPacket, int specifiedTableId) {
        if (tsPacket.getTransportErrorIndicator() == 1) {
            return;
        }
        if (tsPacket.getAdaptationFieldControl() != 1) {
            return;
        }
        Integer pid = tsPacket.getPid();
        byte[] payload = tsPacket.getPayload();
        int preCounter;
        if (tsPacket.getPayloadUnitStartIndicator() == 1) {
            int pointerFieldValue = payload[0];
            int tableId = payload[POINT_FIELD_LENGTH + pointerFieldValue];
            if (tableId != specifiedTableId) {
                mSection = null;
            }
            mSection = new Section();
            byte[] sectionHead = new byte[3];
            sectionHead[0] = payload[pointerFieldValue + 1];
            sectionHead[1] = payload[pointerFieldValue + 2];
            sectionHead[2] = payload[pointerFieldValue + 3];
            mSection.setHeadDate(sectionHead);
            composeSection(payload, pointerFieldValue);
            preCounter = tsPacket.getContinuityCounter();
        } else {
            if (mPidSections.containsKey(pid)) {
                mSection = mPidSections.get(pid);
                preCounter = mPreCounter.get(pid);
            } else {
                return;
            }
            if ((preCounter + 1) % MOD_COUNTER_LENGTH == tsPacket.getContinuityCounter()) {
                composeSection(payload, 0);
                preCounter = tsPacket.getContinuityCounter();
            } else {
                mPidSections.remove(pid);
                mPreCounter.remove(pid);
                mSection = null;
            }
        }

        if (mSection != null) {
            if (mSection.getSectionLength() == mSection.getCurrentLength()) {
                Section completeSection = mSection;
                mSection = null;
                mPidSections.remove(pid);
                mPreCounter.remove(pid);
                composeSpecifiedSection(completeSection, pid);
            } else {
                mPidSections.put(pid, mSection);
                mPreCounter.put(pid, preCounter);
            }
        }
    }

    private void composeSection(byte[] payload, int headOffset) {
        int overSection;
        int startCopyLocation;
        int startCopySectionContentLocation;
        int copyLength;
        if (mSection.getCurrentLength() == 0) {
            overSection = PAYLOAD_VALID_LENGTH - headOffset - POINT_FIELD_LENGTH - SECTION_HEAD - mSection.getSectionLength();
            startCopyLocation = headOffset + POINT_FIELD_LENGTH + SECTION_HEAD;
            startCopySectionContentLocation = 0;
            copyLength = PAYLOAD_VALID_LENGTH - headOffset - POINT_FIELD_LENGTH - SECTION_HEAD;
        } else {
            overSection = PAYLOAD_VALID_LENGTH + mSection.getCurrentLength() - mSection.getSectionLength();
            startCopyLocation = 0;
            startCopySectionContentLocation = mSection.getCurrentLength();
            copyLength = PAYLOAD_VALID_LENGTH;
        }
        if (overSection >= 0) {
            System.arraycopy(payload, startCopyLocation,
                    mSection.getData(), startCopySectionContentLocation, mSection.getSectionLength() - mSection.getCurrentLength());
            mSection.setCurrentLength(mSection.getSectionLength());
        } else {
            System.arraycopy(payload, startCopyLocation,
                    mSection.getData(), startCopySectionContentLocation, copyLength);
            mSection.setCurrentLength(copyLength + mSection.getCurrentLength());
        }

    }

    public void setListener(SectionListener sectionListener) {
        mSectionListener = sectionListener;
    }
}
