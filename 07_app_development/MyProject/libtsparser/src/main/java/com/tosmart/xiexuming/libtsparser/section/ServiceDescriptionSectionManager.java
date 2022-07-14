package com.tosmart.xiexuming.libtsparser.section;


import com.tosmart.xiexuming.libtsparser.tspacket.TsPacket;

import java.util.Observable;
import java.util.Observer;


/**
 * @author xiexuming
 */
public class ServiceDescriptionSectionManager extends SectionManager implements Observer {
    private static final int SERVICE_DESCRIPTION_SPECIAL_HEAD_LENGTH = 8;
    private static final int CRC_CODE_LENGTH = 4;
    private static final int SPECIFIED_SDT_TABLE_ID = 0X42;
    private static final int SDT_PID = 0X11;

    private ServiceDescriptionSection mServiceDescriptionSection;

    @Override
    public void composeSpecifiedSection(Section commonSection, Integer pid) {
        if (commonSection.getCurrentLength() == commonSection.getSectionLength()) {
            byte[] commonSectionContent = commonSection.getData();
            int[] serviceDescriptionFilledHead = new int[9];
            serviceDescriptionFilledHead[0] = commonSection.getTableId();
            serviceDescriptionFilledHead[1] = commonSection.getReserved();
            serviceDescriptionFilledHead[2] = commonSection.getSectionLength();
            serviceDescriptionFilledHead[3] = commonSectionContent[0] << 8 | commonSectionContent[1] & 0xFF;
            serviceDescriptionFilledHead[4] = commonSectionContent[2] >> 1 & 0x1F;
            serviceDescriptionFilledHead[5] = commonSectionContent[2] & 0x01;
            serviceDescriptionFilledHead[6] = commonSectionContent[3];
            serviceDescriptionFilledHead[7] = commonSectionContent[4];
            serviceDescriptionFilledHead[8] = (commonSectionContent[5] & 0xFF) << 8 | (commonSectionContent[6] & 0xFF);

            ServiceDescriptionSection serviceDescriptionSection = new ServiceDescriptionSection();
            serviceDescriptionSection.setHeadDate(serviceDescriptionFilledHead);

            int copyServiceLength = commonSectionContent.length - SERVICE_DESCRIPTION_SPECIAL_HEAD_LENGTH - CRC_CODE_LENGTH;
            byte[] serviceContent = new byte[copyServiceLength];
            System.arraycopy(commonSectionContent, SERVICE_DESCRIPTION_SPECIAL_HEAD_LENGTH, serviceContent, 0, copyServiceLength);
            serviceDescriptionSection.setData(serviceContent);

            int crcStartLocation = commonSectionContent.length - CRC_CODE_LENGTH;
            byte[] crcCode = new byte[4];
            crcCode[0] = commonSectionContent[crcStartLocation];
            crcCode[1] = commonSectionContent[crcStartLocation + 1];
            crcCode[2] = commonSectionContent[crcStartLocation + 2];
            crcCode[3] = commonSectionContent[crcStartLocation + 3];
            serviceDescriptionSection.setCrcCode(crcCode);

            mServiceDescriptionSection = serviceDescriptionSection;
            mSectionListener.makeSdt(mServiceDescriptionSection);
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        TsPacket tsPacket = (TsPacket) o;
        if (tsPacket.getPid() != SDT_PID) {
            return;
        }
        getSection(tsPacket, SPECIFIED_SDT_TABLE_ID);
    }
}

