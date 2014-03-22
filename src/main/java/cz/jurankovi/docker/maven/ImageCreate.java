package cz.jurankovi.docker.maven;

import java.io.File;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import com.kpelykh.docker.client.DockerClient;
import com.kpelykh.docker.client.DockerException;
import com.sun.jersey.api.client.ClientResponse;

@Mojo(name = "image-create", defaultPhase = LifecyclePhase.COMPILE)
public class ImageCreate extends AbstractDockerMojo {

    @Parameter(defaultValue = "src/main/resources/docker")
    private File dockerDir;
    @Parameter(required = true)
    private String tag;

    public void doExecute() throws DockerException, MojoExecutionException {
        if(!dockerDir.isDirectory()) {
            throw new MojoExecutionException("Provided path to Docker folder " + dockerDir.getAbsolutePath() + " doesn't seem to be valid directory path.");
        }
        File dockerFile = new File(dockerDir, "Dockerfile");
        if(!dockerFile.isFile()) {
            throw new MojoExecutionException("Provided path to Docker file " + dockerFile.getAbsolutePath() + " doesn't seem to be valid file path.");
        }
        
        DockerClient client = getClient();
        ClientResponse response = client.build(dockerDir, tag);

    }

}
