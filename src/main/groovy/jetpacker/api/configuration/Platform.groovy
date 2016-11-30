package jetpacker.api.configuration

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.CompileStatic

/**
 * Created by donny on 24/10/2016.
 */
@CompileStatic
@JsonIgnoreProperties(ignoreUnknown = true)
class Platform {
    String name
    String namespace

    String label
    String suffix
    String description

    Version version

    List<Parameter> parameters
}