package at.ac.tuwien.damap.enums;

public enum ELicense {

    CCPUBLICDOMAIN("PD", "Public Domain Mark", "https://creativecommons.org/publicdomain/mark/1.0/"),
    CCZERO("CC0 1.0", "Public Domain Dedication", "https://creativecommons.org/publicdomain/zero/1.0/"),
    PDDL("PDDL", "Open Data Commons Public Domain Dedication and License", "https://opendatacommons.org/license/pddl/summary/"),
    CCBY("CC BY 4.0", "Creative Commons Attribution", "https://creativecommons.org/license/by/4.0/"),
    ODCBY("ODC-By", "Open Data Commons Attribution License", "https://opendatacommons.org/license/by/summary/"),
    CCBYSA("CC BY-SA 4.0", "Creative Commons Attribution-ShareAlike", "https://creativecommons.org/license/by-sa/4.0/"),
    ODBL("ODbL", "Open Data Commons Open Database License", "https://opendatacommons.org/license/odbl/summary/"),
    CCBYND("CC BY-ND 4.0", "Creative Commons Attribution-NoDerivs", "https://creativecommons.org/license/by-nd/4.0/"),
    CCBYNC("CC BY-NC 4.0", "Creative Commons Attribution-NonCommercial", "https://creativecommons.org/license/by-nc/4.0/"),
    CCBYNCSA("CC BY-NC-SA 4.0", "Creative Commons Attribution-NonCommercial-ShareAlike", "https://creativecommons.org/license/by-nc-sa/4.0/"),
    CCBYNCND("CC BY-NC-ND 4.0", "Creative Commons Attribution-NonCommercial-NoDerivs", "https://creativecommons.org/license/by-nc-nd/4.0/"),
    PERLARTISTIC1("Artistic License 1.0", "Artistic License (Perl) 1.0", "https://opensource.org/license/artistic-perl-1-0-2/"),
    PERLARTISTIC2("Artistic-2.0", "Artistic License 2.0", "https://opensource.org/license/artistic-2-0/"),
    GPL2PLUS("GPL-2.0", "GNU General Public License 2 or later", "https://opensource.org/license/GPL-2.0"),
    GPL2("GPL-2.0", "GNU General Public License 2", "https://opensource.org/license/GPL-2.0"),
    GPL3("GPL-3.0", "GNU General Public License 3", "https://opensource.org/license/GPL-3.0"),
    AGPL1("AGPL-1.0", "Affero General Public License 1", "http://www.affero.org/oagpl.html"),
    AGPL3("AGPL-3.0", "Affero General Public License 3", "https://opensource.org/license/agpl-v3/"),
    MPL2("MPL-2.0", "Mozilla Public License 2.0", "https://opensource.org/license/MPL-2.0"),
    LGPL21PLUS("LGPL-2.1", "GNU Library or \"Lesser\" General Public License 2.1 or later", "https://opensource.org/license/LGPL-2.1"),
    LGPL21("LGPL-2.1", "GNU Library or \"Lesser\" General Public License 2.1", "https://opensource.org/license/LGPL-2.1"),
    LGPL3("LGPL-3.0", "GNU Library or \"Lesser\" General Public License 3.0", "https://opensource.org/license/LGPL-3.0"),
    EPL1("EPL-1.0", "Eclipse Public License 1.0", "https://opensource.org/license/EPL-1.0"),
    CDDL1("CDDL-1.0", "Common Development and Distribution License", "https://opensource.org/license/CDDL-1.0"),
    MIT("MIT", "The MIT License", "https://opensource.org/license/mit-license.php"),
    BSD3C("BSD-3-Clause", "The BSD 3-Clause \"New\" or \"Revised\" License", "https://opensource.org/license/BSD-3-Clause"),
    BSD2C("BSD-2-Clause", "The BSD 2-Clause \"Simplified\" or \"FreeBSD\" License", "https://opensource.org/license/BSD-2-Clause"),
    APACHE2("Apache-2.0", "Apache License 2", "https://www.apache.org/licenses/LICENSE-2.0");

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
