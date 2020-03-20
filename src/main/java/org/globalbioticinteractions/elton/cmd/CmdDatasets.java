package org.globalbioticinteractions.elton.cmd;

import com.beust.jcommander.Parameters;
import org.globalbioticinteractions.dataset.Dataset;
import org.globalbioticinteractions.dataset.DatasetFinderException;
import org.globalbioticinteractions.dataset.DatasetRegistry;
import org.globalbioticinteractions.elton.util.DatasetRegistryUtil;
import org.globalbioticinteractions.elton.util.StreamUtil;
import org.globalbioticinteractions.elton.util.TabularWriter;

import java.io.PrintStream;
import java.util.stream.Stream;

@Parameters(separators = "= ", commandDescription = "List Datasets Details")
public class CmdDatasets extends CmdTabularWriterParams {

    public class TsvDatasetWriter implements TabularWriter {
        private final PrintStream out;

        TsvDatasetWriter(PrintStream out) {
            this.out = out;
        }

        public void write(Dataset dataset) {
            Stream<String> datasetInfo = CmdUtil.datasetInfo(dataset).stream();
            String row = StreamUtil.tsvRowOf(datasetInfo);
            out.println(row);
        }

        @Override
        public void writeHeader() {
            Stream<String> datasetHeaders = StreamUtil.datasetHeaderFields();
            out.println(StreamUtil.tsvRowOf(datasetHeaders));
        }
    }

    @Override
    public void run() {
        run(System.out);
    }

    void run(PrintStream out) {
        TsvDatasetWriter serializer = new TsvDatasetWriter(out);
        if (!shouldSkipHeader()) {
            serializer.writeHeader();
        }

        DatasetRegistry registry = DatasetRegistryUtil.forCacheDirOrLocalDir(getCacheDir(), getWorkDir(), createInputStreamFactory());

        try {
            CmdUtil.handleNamespaces(registry,
                    namespace -> serializer.write(registry.datasetFor(namespace)),
                    getNamespaces());
        } catch (DatasetFinderException e) {
            throw new RuntimeException("failed to datasets", e);
        }

    }
}


