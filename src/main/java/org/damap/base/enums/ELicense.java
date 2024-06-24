package org.damap.base.enums;

public enum ELicense {
    
    // TODO: link is dead, fix it
    AGPL1("AGPL-1.0-only", "Affero General Public License v1.0 only", "http://www.affero.org/oagpl.html"),
    AGPL1PLUS("AGPL-1.0-or-later", "Affero General Public License v1.0 or later", "https://spdx.org/licenses/AGPL-1.0-or-later.html"),
    AGPL3("AGPL-3.0-only", "GNU Affero General Public License v3.0 only", "https://www.gnu.org/licenses/agpl.txt"),
    AGPL3PLUS("AGPL-3.0-or-later", "GNU Affero General Public License v3.0 or later", "https://spdx.org/licenses/AGPL-3.0-or-later.html"),
    APACHE1("Apache-1.0", "Apache License 1.0", "http://www.apache.org/licenses/LICENSE-1.0"),
    APACHE1_1("Apache-1.1", "Apache License 1.1", "http://apache.org/licenses/LICENSE-1.1"),
    APACHE2("Apache-2.0", "Apache License 2.0", "http://www.apache.org/licenses/LICENSE-2.0"),
    ARTISTIC1("Artistic License 1.0", "Artistic License 1.0", "https://opensource.org/licenses/Artistic-1.0"),
    ARTISTIC1PERL("Artistic-1.0-Perl", "Artistic License 1.0 (Perl)", "http://dev.perl.org/licenses/artistic.html"),
    ARTISTIC2("Artistic-2.0", "Artistic License 2.0", "http://www.perlfoundation.org/artistic_license_2_0"),
    BSD2C("BSD-2-Clause", "BSD 2-Clause \"Simplified\" License", "https://opensource.org/licenses/BSD-2-Clause"),
    BSD3C("BSD-3-Clause", "BSD 3-Clause \"New\" or \"Revised\" License", "https://opensource.org/licenses/BSD-3-Clause"),
    CCBY("CC-BY-4.0", "Creative Commons Attribution 4.0 International", "https://creativecommons.org/licenses/by/4.0/legalcode"),
    CCBYNC("CC-BY-NC-4.0", "Creative Commons Attribution Non Commercial 4.0 International", "https://creativecommons.org/licenses/by-nc/4.0/legalcode"),
    CCBYNCND("CC-BY-NC-ND-4.0", "Creative Commons Attribution Non Commercial No Derivatives 4.0 International", "https://creativecommons.org/licenses/by-nc-nd/4.0/legalcode"),
    CCBYNCSA("CC-BY-NC-SA-4.0", "Creative Commons Attribution Non Commercial Share Alike 4.0 International", "https://creativecommons.org/licenses/by-nc-sa/4.0/legalcode"),
    CCBYND("CC-BY-ND-4.0", "Creative Commons Attribution No Derivatives 4.0 International", "https://creativecommons.org/licenses/by-nd/4.0/legalcode"),
    CCBYSA("CC-BY-SA-4.0", "Creative Commons Attribution Share Alike 4.0 International", "https://creativecommons.org/licenses/by-sa/4.0/legalcode"),
    CCPUBLICDOMAIN("PD", "Public Domain Mark", "https://creativecommons.org/publicdomain/mark/1.0/"),
    CCZERO("CC0-1.0", "Creative Commons Zero v1.0 Universal", "https://creativecommons.org/publicdomain/zero/1.0/legalcode"),
    // TODO: link is dead, fix it
    CDDL1("CDDL-1.0", "Common Development and Distribution License 1.0", "https://opensource.org/licenses/cddl1"),
    // TODO: link is dead, fix it
    CDDL1_1("CDDL-1.1", "Common Development and Distribution License 1.1", "http://glassfish.java.net/public/CDDL+GPL_1_1.html"),
    EPL1("EPL-1.0", "Eclipse Public License 1.0", "http://www.eclipse.org/legal/epl-v10.html"),
    EPL2("EPL-2.0", "Eclipse Public License 2.0", "https://www.eclipse.org/legal/epl-2.0"),
    GPL2("GPL-2.0-only", "GNU General Public License v2.0 only", "https://www.gnu.org/licenses/old-licenses/gpl-2.0-standalone.html"),
    GPL2PLUS("GPL-2.0-plus", "GNU General Public License v2.0 or later", "https://www.gnu.org/licenses/old-licenses/gpl-2.0-standalone.html"),
    GPL3("GPL-3.0-only", "GNU General Public License v3.0 only", "https://www.gnu.org/licenses/gpl-3.0-standalone.html"),
    GPL3PLUS("GPL-3.0-or-later", "GNU General Public License v3.0 or later", "https://spdx.org/licenses/GPL-3.0-or-later.html"),
    LGPL2("LGPL-2.0-only", "GNU Library General Public License v2 only", "https://www.gnu.org/licenses/old-licenses/lgpl-2.0-standalone.html"),
    LGPL2PLUS("LGPL-2.0-or-later", "GNU Library General Public License v2 or later", "https://spdx.org/licenses/LGPL-2.0-or-later.html"),
    LGPL2_1("LGPL-2.1-only", "GNU Lesser General Public License v2.1 only", "https://www.gnu.org/licenses/old-licenses/lgpl-2.1-standalone.html"),
    LGPL2_1PLUS("LGPL-2.1-or-later", "GNU Lesser General Public License v2.1 or later", "https://spdx.org/licenses/LGPL-2.1-or-later.html"),
    LGPL3("LGPL-3.0-only", "GNU Lesser General Public License v3.0 only", "https://www.gnu.org/licenses/lgpl-3.0-standalone.html"),
    LGPL3PLUS("LGPL-3.0-or-later", "GNU Lesser General Public License v3.0 or later", "https://spdx.org/licenses/LGPL-3.0-or-later.html"),
    MIT("MIT", "MIT License", "https://opensource.org/licenses/MIT"),
    MPL1("MPL-1.0", "Mozilla Public License 1.0", "http://www.mozilla.org/MPL/MPL-1.0.html"),
    MPL1_1("MPL-1.1", "Mozilla Public License 1.1", "https://www.mozilla.org/MPL/1.1/"),
    MPL2("MPL-2.0", "Mozilla Public License 2.0", "http://www.mozilla.org/MPL/2.0/"),
    ODBL("ODbL-1.0", "Open Data Commons Open Database License v1.0", "http://www.opendatacommons.org/licenses/odbl/1.0/"),
    ODCBY("ODC-BY-1.0", "Open Data Commons Attribution License v1.0", "https://opendatacommons.org/licenses/by/1.0/"),
    PDDL("PDDL-1.0", "Open Data Commons Public Domain Dedication and License", "https://opendatacommons.org/licenses/pddl/1-0/");

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
