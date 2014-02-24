package cz.jurankovi.docker.maven;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import com.kpelykh.docker.client.DockerException;

@Mojo(name="container-stop")
public class ContainerStopMojo extends AbstractDockerMojo {

    @Parameter(required=true)
    private String containerId;

    public void execute() throws MojoExecutionException {
        try {
            getClient().stopContainer(containerId);
        } catch (DockerException e) {
            // TODO log
            throw new MojoExecutionException(e.getMessage());
        }
    }
}