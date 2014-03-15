package cz.jurankovi.docker.maven;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Parameter;

import com.kpelykh.docker.client.DockerClient;
import com.kpelykh.docker.client.DockerException;

public abstract class AbstractDockerMojo extends AbstractMojo  {

    @Parameter(defaultValue="http://127.0.0.1:4243", required=false)
    protected String dockerUrl;
    
    protected DockerClient getClient() {
        return new DockerClient(dockerUrl);
    }
    
    public void execute() throws MojoExecutionException {
        try {
            doExecute();
        } catch (DockerException e) {
            // TODO log
            throw new MojoExecutionException(e.getMessage());
        }
    }
    
    public abstract void doExecute() throws DockerException, MojoExecutionException;
    
}
