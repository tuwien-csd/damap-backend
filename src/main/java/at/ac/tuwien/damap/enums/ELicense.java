package at.ac.tuwien.damap.enums;

public enum ELicense {

    CCPUBLICDOMAIN("PD", "Public Domain Mark", "https://creativecommons.org/publicdomain/mark/1.0/"),
    CCZERO("CC0-1.0", "Creative Commons Zero v1.0 Universal", "https://creativecommons.org/publicdomain/zero/1.0/legalcode"),
    PDDL("PDDL-1.0", "Open Data Commons Public Domain Dedication and License", "https://opendatacommons.org/licenses/pddl/1-0/"),
    CCBY("CC-BY-4.0", "Creative Commons Attribution", "https://creativecommons.org/licenses/by/4.0/legalcode"),
    ODCBY("ODC-BY-1.0", "Open Data Commons Attribution License v1.0", "https://opendatacommons.org/licenses/by/1.0/"),
    CCBYSA("CC-BY-SA-4.0", "Creative Commons Attribution Share Alike 4.0 International", "https://creativecommons.org/licenses/by-sa/4.0/legalcode"),
    ODBL("ODbL-1.0", "Open Data Commons Open Database License v1.0", "http://www.opendatacommons.org/licenses/odbl/1.0/"),
    CCBYND("CC-BY-ND-4.0", "Creative Commons Attribution-NoDerivs", "https://creativecommons.org/licenses/by-nd/4.0/legalcode"),
    CCBYNC("CC BY-NC-4.0", "Creative Commons Attribution-NonCommercial", "https://creativecommons.org/licenses/by-nc/4.0/legalcode"),
    CCBYNCSA("CC-BY-NC-SA-4.0", "Creative Commons Attribution-NonCommercial-ShareAlike", "https://creativecommons.org/licenses/by-nc-sa/4.0/legalcode"),
    CCBYNCND("CC-BY-NC-ND-4.0", "Creative Commons Attribution-NonCommercial-NoDerivs", "https://creativecommons.org/licenses/by-nc-nd/4.0/legalcode"),
    PERLARTISTIC1("Artistic-1.0-Perl", "Artistic License (Perl) 1.0", "http://dev.perl.org/licenses/artistic.html"),
    ARTISTIC2("Artistic-2.0", "Artistic License 2.0", "http://www.perlfoundation.org/artistic_license_2_0"),
    GPL2PLUS("GPL-2.0-plus", "GNU General Public License v2.0 or later", "https://www.gnu.org/licenses/old-licenses/gpl-2.0-standalone.html"),
    GPL2("GPL-2.0-only", "GNU General Public License v2.0 only", "https://www.gnu.org/licenses/old-licenses/gpl-2.0-standalone.html"),
    GPL3("GPL-3.0-only", "GNU General Public License v3.0 only", "https://www.gnu.org/licenses/gpl-3.0-standalone.html"),
    AGPL1("AGPL-1.0-only", "Affero General Public License v1.0 only", "http://www.affero.org/oagpl.html"),
    AGPL3("AGPL-3.0", "GNU Affero General Public License v3.0 only", "https://www.gnu.org/licenses/agpl.txt"),
    MPL2("MPL-2.0", "Mozilla Public License 2.0", "http://www.mozilla.org/MPL/2.0/"),
    LGPL2_1PLUS("LGPL-2.1-or-later", "GNU Lesser General Public License v2.1 or later", "https://www.gnu.org/licenses/old-licenses/lgpl-2.1-standalone.html"),
    LGPL2_1("LGPL-2.1-only", "GNU Lesser General Public License v2.1 only", "https://www.gnu.org/licenses/old-licenses/lgpl-2.1-standalone.html"),
    LGPL3("LGPL-3.0", "GNU Lesser General Public License v3.0 only", "https://www.gnu.org/licenses/lgpl-3.0-standalone.html"),
    EPL1("EPL-1.0", "Eclipse Public License 1.0", "http://www.eclipse.org/legal/epl-v10.html"),
    // TODO: link is dead, fix it
    CDDL1("CDDL-1.0", "Common Development and Distribution License 1.0", "https://opensource.org/licenses/cddl1"),
    MIT("MIT", "MIT License", "https://opensource.org/licenses/MIT"),
    BSD3C("BSD-3-Clause", "BSD 3-Clause \"New\" or \"Revised\" License", "https://opensource.org/licenses/BSD-3-Clause"),
    BSD2C("BSD-2-Clause", "BSD 2-Clause \"Simplified\" License", "https://opensource.org/licenses/BSD-2-Clause"),
    APACHE2("Apache-2.0", "Apache License 2.0", "http://www.apache.org/licenses/LICENSE-2.0");

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
