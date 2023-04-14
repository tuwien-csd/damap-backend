package at.ac.tuwien.damap.enums;

public enum ELicense {

    CCPUBLICDOMAIN("PD", "Public Domain Mark", "https://creativecommons.org/publicdomain/mark/1.0/"),
    CCZERO("CC Zero", "Public Domain Dedication", "https://creativecommons.org/publicdomain/zero/1.0/"),
    PDDL("PDDL", "Open Data Commons Public Domain Dedication and License", "https://opendatacommons.org/licenses/pddl/summary/"),
    CCBY("CC-BY", "Creative Commons Attribution", "https://creativecommons.org/licenses/by/4.0/"),
    ODCBY("ODC-By", "Open Data Commons Attribution License", "https://opendatacommons.org/licenses/by/summary/"),
    CCBYSA("CC-BY-SA", "Creative Commons Attribution-ShareAlike", "https://creativecommons.org/licenses/by-sa/4.0/"),
    ODBL("ODbL", "Open Data Commons Open Database License", "https://opendatacommons.org/licenses/odbl/summary/"),
    CCBYND("CC-BY-ND", "Creative Commons Attribution-NoDerivs", "https://creativecommons.org/licenses/by-nd/4.0/"),
    CCBYNC("CC-BY-NC", "Creative Commons Attribution-NonCommercial", "https://creativecommons.org/licenses/by-nc/4.0/"),
    CCBYNCSA("CC-BY-NC-SA", "Creative Commons Attribution-NonCommercial-ShareAlike", "https://creativecommons.org/licenses/by-nc-sa/4.0/"),
    CCBYNCND("CC-BY-NC-ND", "Creative Commons Attribution-NonCommercial-NoDerivs", "https://creativecommons.org/licenses/by-nc-nd/4.0/"),
    PERLARTISTIC1("Artistic License 1.0", "Artistic License 1.0", "https://opensource.org/licenses/Artistic-Perl-1.0"),
    PERLARTISTIC2("Artistic License 2.0", "Artistic License 2.0", "https://opensource.org/licenses/Artistic-2.0"),
    GPL2PLUS("GPL-2.0", "GNU General Public License 2 or later", "https://opensource.org/licenses/GPL-2.0"),
    GPL2("GPL-2.0", "GNU General Public License 2", "https://opensource.org/licenses/GPL-2.0"),
    GPL3("GPL-3.0", "GNU General Public License 3", "https://opensource.org/licenses/GPL-3.0"),
    AGPL1("AGPL-1.0", "Affero General Public License 1", ""),
    AGPL3("AGPL-3.0", "Affero General Public License 3", "https://opensource.org/licenses/AGPL-3.0"),
    MPL2("Mozilla Public License 2.0", "Mozilla Public License 2.0", "https://opensource.org/licenses/MPL-2.0"),
    LGPL21PLUS("LGPL-2.1", "GNU Library or \"Lesser\" General Public License 2.1 or later", "https://opensource.org/licenses/LGPL-2.1"),
    LGPL21("LGPL-2.1", "GNU Library or \"Lesser\" General Public License 2.1", "https://opensource.org/licenses/LGPL-2.1"),
    LGPL3("LGPL-3.0", "GNU Library or \"Lesser\" General Public License 3.0", "https://opensource.org/licenses/LGPL-3.0"),
    EPL1("EPL-1.0", "Eclipse Public License 1.0", "https://opensource.org/licenses/EPL-1.0"),
    CDDL1("CDDL-1.0", "Common Development and Distribution License", "https://opensource.org/licenses/CDDL-1.0"),
    MIT("MIT", "The MIT License", "https://opensource.org/licenses/mit-license.php"),
    BSD3C("BSD", "The BSD 3-Clause \"New\" or \"Revised\" License", "https://opensource.org/licenses/BSD-3-Clause"),
    BSD2C("The BSD 2-Clause \"Simplified\" or \"FreeBSD\" License", "The BSD 2-Clause \"Simplified\" or \"FreeBSD\" License", "https://opensource.org/licenses/BSD-2-Clause"),
    APACHE2("Apache License 2", "Apache License 2", "https://www.apache.org/licenses/LICENSE-2.0");

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
