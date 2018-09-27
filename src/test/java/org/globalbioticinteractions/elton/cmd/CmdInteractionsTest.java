package org.globalbioticinteractions.elton.cmd;

import com.beust.jcommander.JCommander;
import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.URISyntaxException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.junit.Assert.assertThat;

public class CmdInteractionsTest {

    @Test
    public void interactionsNoHeader() throws URISyntaxException {
        JCommander jc = new CmdLine().buildCommander();
        jc.parse("interactions",
                "--cache-dir=" + CmdTestUtil.cacheDirTest(),
                "--skip-header",
                "globalbioticinteractions/template-dataset");

        Assert.assertEquals(jc.getParsedCommand(), "interactions");

        JCommander actual = jc.getCommands().get(jc.getParsedCommand());
        Assert.assertEquals(actual.getObjects().size(), 1);
        Object cmd = actual.getObjects().get(0);
        Assert.assertEquals(cmd.getClass(), CmdInteractions.class);
        CmdInteractions cmdInteractions = (CmdInteractions) actual.getObjects().get(0);
        assertThat(cmdInteractions.getNamespaces(), hasItem("globalbioticinteractions/template-dataset"));

        if (actual.getObjects().get(0) instanceof Runnable) {
            ByteArrayOutputStream out1 = new ByteArrayOutputStream();
            PrintStream out = new PrintStream(out1);
            ((CmdInteractions) actual.getObjects().get(0)).run(out);
            assertThat(out1.toString(), startsWith("\tLeptoconchus incycloseris\t\t\t\t\thttp://purl.obolibrary.org/obo/RO_0002444\tparasiteOf\t\tFungia (Cycloseris) costulata\t\t\t\t\t\t\t\t\t\t10.1007/s13127-011-0039-1\thttps://doi.org/10.1007/s13127-011-0039-1\tGittenberger, A., Gittenberger, E. (2011). Cryptic, adaptive radiation of endoparasitic snails: sibling species of Leptoconchus (Gastropoda: Coralliophilidae) in corals. Org Divers Evol, 11(1), 21–41. doi:10.1007/s13127-011-0039-1\tglobalbioticinteractions/template-dataset\tJorrit H. Poelen. 2014. Species associations manually extracted from literature.\thttps://zenodo.org/record/207958/files/globalbioticinteractions/template-dataset-0.0.2.zip\t2017-09-19T17:01:39Z\t631d3777cf83e1abea848b59a6589c470cf0c7d0fd99682c4c104481ad9a543f\tdev\n"));
        }
    }

    @Test
    public void interactionsWithHeader() throws URISyntaxException {
        JCommander jc = new CmdLine().buildCommander();
        jc.parse("interactions",
                "--cache-dir=" + CmdTestUtil.cacheDirTest(),
                "globalbioticinteractions/template-dataset");
        JCommander actual = jc.getCommands().get(jc.getParsedCommand());

        if (actual.getObjects().get(0) instanceof Runnable) {
            ByteArrayOutputStream out1 = new ByteArrayOutputStream();
            PrintStream out = new PrintStream(out1);
            ((CmdInteractions) actual.getObjects().get(0)).run(out);
            String actual1 = out1.toString();
            String[] lines = StringUtils.splitByWholeSeparator(actual1, "\n");
            assertThat(lines[0], is("sourceTaxonId\tsourceTaxonName\tsourceTaxonRank\tsourceTaxonPath\tsourceTaxonPathIds\tsourceTaxonPathNames\tinteractionTypeId\tinteractionTypeName\ttargetTaxonId\ttargetTaxonName\ttargetTaxonRank\ttargetTaxonPath\ttargetTaxonPathIds\ttargetTaxonPathNames\thttp://rs.tdwg.org/dwc/terms/eventDate\tdecimalLatitude\tdecimalLongitude\tlocalityId\tlocalityName\treferenceDoi\treferenceUrl\treferenceCitation\tnamespace\tcitation\tarchiveURI\tlastSeenAt\tcontentHash\teltonVersion"));
            assertThat(lines[1], startsWith("\tLeptoconchus incycloseris\t\t\t\t\thttp://purl.obolibrary.org/obo/RO_0002444\tparasiteOf\t\tFungia (Cycloseris) costulata\t\t\t\t\t\t\t\t\t\t10.1007/s13127-011-0039-1\thttps://doi.org/10.1007/s13127-011-0039-1\tGittenberger, A., Gittenberger, E. (2011). Cryptic, adaptive radiation of endoparasitic snails: sibling species of Leptoconchus (Gastropoda: Coralliophilidae) in corals. Org Divers Evol, 11(1), 21–41. doi:10.1007/s13127-011-0039-1\tglobalbioticinteractions/template-dataset\tJorrit H. Poelen. 2014. Species associations manually extracted from literature.\thttps://zenodo.org/record/207958/files/globalbioticinteractions/template-dataset-0.0.2.zip\t2017-09-19T17:01:39Z\t631d3777cf83e1abea848b59a6589c470cf0c7d0fd99682c4c104481ad9a543f\tdev"));
            assertThat(lines[0].split("\t").length, is(lines[1].split("\t").length));
        }
    }


}