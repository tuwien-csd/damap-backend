package at.ac.tuwien.damap.enums;

public enum ELicense {

    ccPublicDomain("PD", "Public Domain Mark", "https://creativecommons.org/publicdomain/mark/1.0/"),
    ccZero("CC Zero", "Public Domain Dedication", "https://creativecommons.org/publicdomain/zero/1.0/"),
    pddl("PDDL", "Open Data Commons Public Domain Dedication and License", "https://opendatacommons.org/licenses/pddl/summary/"),
    ccBy("CC-BY", "Creative Commons Attribution", "https://creativecommons.org/licenses/by/4.0/"),
    odcBy("ODC-By", "Open Data Commons Attribution License", "https://opendatacommons.org/licenses/by/summary/"),
    ccBySa("CC-BY-SA", "Creative Commons Attribution-ShareAlike", "https://creativecommons.org/licenses/by-sa/4.0/"),
    odbl("ODbL", "Open Data Commons Open Database License", "https://opendatacommons.org/licenses/odbl/summary/"),
    ccByNd("CC-BY-ND", "Creative Commons Attribution-NoDerivs", "https://creativecommons.org/licenses/by-nd/4.0/"),
    ccByNc("CC-BY-NC", "Creative Commons Attribution-NonCommercial", "https://creativecommons.org/licenses/by-nc/4.0/"),
    ccByNcSa("CC-BY-NC-SA", "Creative Commons Attribution-NonCommercial-ShareAlike", "https://creativecommons.org/licenses/by-nc-sa/4.0/"),
    ccByNcNd("CC-BY-NC-ND", "Creative Commons Attribution-NonCommercial-NoDerivs", "https://creativecommons.org/licenses/by-nc-nd/4.0/"),
    perlArtistic1("Artistic License 1.0", "Artistic License 1.0", "https://opensource.org/licenses/Artistic-Perl-1.0"),
    perlArtistic2("Artistic License 2.0", "Artistic License 2.0", "https://opensource.org/licenses/Artistic-2.0"),
    gpl2plus("GPL-2.0", "GNU General Public License 2 or later", "https://opensource.org/licenses/GPL-2.0"),
    gpl2("GPL-2.0", "GNU General Public License 2", "https://opensource.org/licenses/GPL-2.0"),
    gpl3("GPL-3.0", "GNU General Public License 3", "https://opensource.org/licenses/GPL-3.0"),
    agpl1("AGPL-1.0", "Affero General Public License 1", ""),
    agpl3("AGPL-3.0", "Affero General Public License 3", "https://opensource.org/licenses/AGPL-3.0"),
    mpl2("Mozilla Public License 2.0", "Mozilla Public License 2.0", "https://opensource.org/licenses/MPL-2.0"),
    lgpl21plus("LGPL-2.1", "GNU Library or \"Lesser\" General Public License 2.1 or later", "https://opensource.org/licenses/LGPL-2.1"),
    lgpl21("LGPL-2.1", "GNU Library or \"Lesser\" General Public License 2.1", "https://opensource.org/licenses/LGPL-2.1"),
    lgpl3("LGPL-3.0", "GNU Library or \"Lesser\" General Public License 3.0", "https://opensource.org/licenses/LGPL-3.0"),
    epl1("EPL-1.0", "Eclipse Public License 1.0", "https://opensource.org/licenses/EPL-1.0"),
    cddl1("CDDL-1.0", "Common Development and Distribution License", "https://opensource.org/licenses/CDDL-1.0"),
    mit("MIT", "The MIT License", "https://opensource.org/licenses/mit-license.php"),
    bsd3c("BSD", "The BSD 3-Clause \"New\" or \"Revised\" License", "https://opensource.org/licenses/BSD-3-Clause"),
    bsd2c("The BSD 2-Clause \"Simplified\" or \"FreeBSD\" License", "The BSD 2-Clause \"Simplified\" or \"FreeBSD\" License", "https://opensource.org/licenses/BSD-2-Clause"),
    apache2("Apache License 2", "Apache License 2", "https://www.apache.org/licenses/LICENSE-2.0");

    private final String acronym;
    private final String name;
    private final String url;

    ELicense(String acronym, String name, String url){
        this.acronym = acronym;
        this.name = name;
        this.url = url;
    }

    public String getAcronym() {
        return this.acronym;
    }

    public String getName() {
        return this.name;
    }

    public String getUrl() {
        return this.url;
    }

}
