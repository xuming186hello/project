package com.tosmart.xiexuming.libtsparser.section;

/**
 * @author xiexuming
 */
public interface SectionListener {
    /**
     * 组建pat
     *
     * @param programAssociationSection pas
     */
    void makePat(ProgramAssociationSection programAssociationSection);

    /**
     * 组建pat
     * @param pid pid
     * @param programMapSection 特定section
     */
    void makePmt(int pid, ProgramMapSection programMapSection);

    /**
     * 组建sdt
     * @param serviceDescriptionSection 特定section
     */
    void makeSdt(ServiceDescriptionSection serviceDescriptionSection);
}