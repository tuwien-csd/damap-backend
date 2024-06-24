package org.damap.base.enums;

import java.util.ArrayList;
import java.util.List;

public enum EFunderIds {

    EU_FUNDREF_ID("501100000780"),
    EU_ROR_ID("https://ror.org/032s10s29"),
    EU_ISNI_ID("0000 0004 6090 9785"),

    FWF_FUNDREF_ID("501100002428"),
    FWF_ROR_ID("https://ror.org/013tf3c58"),
    FWF_ISNI_ID("0000 0001 1091 8438");


    private final String funderId;

    private static final List<String> EUFunderIds = new ArrayList<>();
    private static final List<String> FWFFunderIds = new ArrayList<>();

    static {
        EUFunderIds.add(EU_FUNDREF_ID.funderId);
        EUFunderIds.add(EU_ROR_ID.funderId);
        EUFunderIds.add(EU_ISNI_ID.funderId);

        FWFFunderIds.add(FWF_FUNDREF_ID.funderId);
        FWFFunderIds.add(FWF_ROR_ID.funderId);
        FWFFunderIds.add(FWF_ISNI_ID.funderId);
    }

    EFunderIds(String funderId) {
        this.funderId = funderId;
    }

    public static List<String> getEUFunderIds() {return EUFunderIds;}
    public static List<String> getFWFFunderIds() {return FWFFunderIds;}
    @Override
    public String toString() {
        return funderId;
    }
}
