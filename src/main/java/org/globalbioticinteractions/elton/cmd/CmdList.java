package org.globalbioticinteractions.elton.cmd;

import com.beust.jcommander.Parameters;
import org.apache.commons.lang.StringUtils;
import org.eol.globi.service.DatasetFinderException;
import org.eol.globi.service.DatasetRegistry;
import org.eol.globi.service.DatasetRegistryGitHubArchive;
import org.eol.globi.service.DatasetRegistryProxy;
import org.eol.globi.service.DatasetRegistryZenodo;
import org.globalbioticinteractions.elton.util.DatasetFinderUtil;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Parameters(separators = "= ", commandDescription = "List Available Datasets")
public class CmdList extends CmdOnlineParams {

    @Override
    public void run() {
        run(getStdout());
    }

    public void run(PrintStream out) {
        DatasetRegistry finderLocal = DatasetFinderUtil.forCacheDirOrLocalDir(getCacheDir(), getWorkDir());
        DatasetRegistry finder = isOnline()
                ? new DatasetRegistryProxy(Arrays.asList(new DatasetRegistryZenodo(), new DatasetRegistryGitHubArchive(), finderLocal))
                : finderLocal;
        try {
            List<String> namespaces = finder.findNamespaces()
                    .stream()
                    .filter(StringUtils::isNotEmpty)
                    .distinct()
                    .sorted()
                    .collect(Collectors.toList());
            out.println(StringUtils.join(namespaces, "\n"));
        } catch (DatasetFinderException e) {
            throw new RuntimeException(e);
        }
    }


}
