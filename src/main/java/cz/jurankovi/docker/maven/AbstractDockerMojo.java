package cz.jurankovi.docker.maven;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Parameter;

import com.kpelykh.docker.client.DockerClient;

public abstract class AbstractDockerMojo extends AbstractMojo  {

    @Parameter(defaultValue="http://127.0.0.1:4243", required=false)
    protected String dockerUrl;
    
    protected DockerClient getClient() {
        return new DockerClient(dockerUrl);
    }
    
    public abstract void execute() throws MojoExecutionException;
    
}
