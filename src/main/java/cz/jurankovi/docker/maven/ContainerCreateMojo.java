package cz.jurankovi.docker.maven;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import com.kpelykh.docker.client.DockerClient;
import com.kpelykh.docker.client.DockerException;
import com.kpelykh.docker.client.model.ContainerConfig;
import com.kpelykh.docker.client.model.ContainerCreateResponse;

@Mojo(name = "container-create", defaultPhase = LifecyclePhase.PRE_INTEGRATION_TEST)
public class ContainerCreateMojo extends AbstractDockerMojo {

    @Parameter(required = true)
    private String imageId;
    @Parameter(required = true)
    private String command;
    private String hostName;

    public void execute() throws MojoExecutionException {
        try {
            ContainerConfig cfg = new ContainerConfig();
            cfg.setImage(imageId);
            cfg.setCmd(new String[] { command });
            if (hostName != null && !hostName.isEmpty())
                cfg.setHostName(hostName);
            DockerClient client = getClient();
            ContainerCreateResponse resp = client.createContainer(cfg);
            //TODO log response
        } catch (DockerException e) {
            // TODO log
            throw new MojoExecutionException(e.getMessage());
        }
    }
}
