package jetpacker.api.configuration

import groovy.transform.CompileStatic
import groovy.transform.ToString

/**
 * Created by donny on 24/10/2016.
 */
@ToString(includeNames = true)
@CompileStatic
class Application extends Metadata {
    String namespace
    Endpoint endpoint = Endpoint.None
}