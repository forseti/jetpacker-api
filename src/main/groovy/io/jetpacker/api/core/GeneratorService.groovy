package io.jetpacker.api.core

import groovy.util.logging.Slf4j
import io.jetpacker.api.configuration.JetpackerProperties
import io.jetpacker.api.configuration.container.Container
import io.jetpacker.api.configuration.kit.Kit
import io.jetpacker.api.configuration.kit.Kits
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service

import javax.annotation.PostConstruct
import java.util.concurrent.ExecutionException

/**
 * Created by donny on 22/11/16.
 */
@Slf4j
@Service
class GeneratorService {
    private final JetpackerProperties jetpackerProperties
    private final RepositoryService repositoryService
    private final Resource[] resources

    @Autowired
    GeneratorService(RepositoryService repositoryService,
                     JetpackerProperties jetpackerProperties,
                     Resource[] resources) {
        this.repositoryService = repositoryService
        this.jetpackerProperties = jetpackerProperties
        this.resources = resources
    }

    @PostConstruct
    void setUp() {
        try {
            Kits kits = jetpackerProperties.kits

            List<Kit> nonJavaKits = [ kits.node,
                                      kits.node.dependency,
                                      kits.node.extensions,
                                      kits.guard,
                                      kits.guard.dependency ].flatten()

            log.info "Loading candidates from SDKMAN!"
            kits.openjdk.extensions = repositoryService.loadCandidatesFromSdkMan()

            nonJavaKits.each { Kit kit ->
                log.info "Updating releases for ${kit.label}"
                repositoryService.updateReleases(kit)
            }

            jetpackerProperties.containers.values().each { Container container ->
                log.info "Updating releases for ${container.label}"
                repositoryService.updateReleases(container)
            }
        } catch (InterruptedException|ExecutionException e) {
            e.printStackTrace()
        }
    }

    JetpackerProperties load() {
        jetpackerProperties
    }
}