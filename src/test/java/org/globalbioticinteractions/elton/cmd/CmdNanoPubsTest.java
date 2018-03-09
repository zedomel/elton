package org.globalbioticinteractions.elton.cmd;

import com.beust.jcommander.JCommander;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.URISyntaxException;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CmdNanoPubsTest {

    @Test
    public void interactions() throws URISyntaxException {
        JCommander jc = new CmdLine().buildCommander();
        jc.parse("nanopubs", "--cache-dir=" + CmdTestUtil.cacheDirTest(), "globalbioticinteractions/template-dataset");

        Assert.assertEquals(jc.getParsedCommand(), "nanopubs");

        JCommander actual = jc.getCommands().get(jc.getParsedCommand());
        Assert.assertEquals(actual.getObjects().size(), 1);
        Object cmd = actual.getObjects().get(0);
        Assert.assertEquals(cmd.getClass(), CmdNanoPubs.class);
        CmdNanoPubs cmdNames = (CmdNanoPubs) actual.getObjects().get(0);
        assertThat(cmdNames.getNamespaces().size(), is(1));
        assertThat(cmdNames.getNamespaces(), hasItem("globalbioticinteractions/template-dataset"));
        cmdNames.setIdGenerator(() -> "1");
        if (actual.getObjects().get(0) instanceof Runnable) {
            ByteArrayOutputStream out1 = new ByteArrayOutputStream();
            PrintStream out = new PrintStream(out1);
            ((CmdInteractions) actual.getObjects().get(0)).run(out);
            System.err.println(out1.toString().split("\\n")[0]);
            String firstLine = "@prefix this: <http://purl.org/np/RA22U4gNPjSqscA5kRAYqUffE_19tUT7TYyM3prs3UnG0> . @prefix sub: <http://purl.org/np/RA22U4gNPjSqscA5kRAYqUffE_19tUT7TYyM3prs3UnG0#> . @prefix np: <http://www.nanopub.org/nschema#> . @prefix dcterms: <http://purl.org/dc/terms/> . @prefix prov: <http://www.w3.org/ns/prov#> . @prefix pav: <http://swan.mindinformatics.org/ontologies/1.2/pav/> . @prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> . @prefix obo: <http://purl.obolibrary.org/obo/> .  sub:Head { \tthis: np:hasAssertion sub:Assertion ; \t\tnp:hasProvenance sub:Provenance ; \t\tnp:hasPublicationInfo sub:Pubinfo ; \t\ta np:Nanopublication . }  sub:Assertion { \tsub:Interaction obo:RO_0000057 sub:Organism_1 , sub:Organism_2 ; \t\ta obo:GO_0044419 . \t \tsub:Organism_1 obo:RO_0002444 sub:Organism_2 ; \t\trdfs:label \"Leptoconchus incycloseris\" . \t \tsub:Organism_2 rdfs:label \"Fungia (Cycloseris) costulata\" . }  sub:Provenance { \tsub:Assertion prov:wasDerivedFrom <https://doi.org/10.1007/s13127-011-0039-1> . \t \t<https://doi.org/10.1007/s13127-011-0039-1> dcterms:bibliographicCitation \"Gittenberger, A., Gittenberger, E. (2011). Cryptic, adaptive radiation of endoparasitic snails: sibling species of Leptoconchus (Gastropoda: Coralliophilidae) in corals. Org Divers Evol, 11(1), 21&#8211;41. doi:10.1007/s13127-011-0039-1\" . }  sub:Pubinfo { \tthis: pav:createdBy <https://doi.org/10.5281/zenodo.998263> ; \t\tprov:wasDerivedFrom <https://doi.org/10.5281/zenodo.207958> . \t \t<https://doi.org/10.5281/zenodo.207958> dcterms:bibliographicCitation \"Jorrit H. Poelen. 2014. Species associations manually extracted from literature.\" . }";
            assertThat(StringUtils.trim(out1.toString().split("\\n")[0]), is(firstLine));
        }
    }


}