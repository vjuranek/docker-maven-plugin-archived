package cz.jurankovi.docker.maven;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import com.kpelykh.docker.client.DockerClient;
import com.kpelykh.docker.client.DockerException;
import com.kpelykh.docker.client.model.ContainerConfig;
import com.kpelykh.docker.client.model.ContainerCreateResponse;

@Mojo(name = "container-create", defaultPhase = LifecyclePhase.PRE_INTEGRATION_TEST)
public class ContainerCreateMojo extends AbstractDockerMojo {

    public static final String PROPERTY_CONTAINER_ID = "__docker_container_id__"; 
    
    @Parameter(required = true)
    private String imageId;
    @Parameter(required = true)
    private String command;
    private String hostName;
    
    @Component
    private MavenProject project;

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
            exportContainerId(resp.getId());
        } catch (DockerException e) {
            // TODO log
            throw new MojoExecutionException(e.getMessage());
        }
    }
    
    private void exportContainerId(String containerId) {
        project.getProperties().put(PROPERTY_CONTAINER_ID, containerId);
    }
}
