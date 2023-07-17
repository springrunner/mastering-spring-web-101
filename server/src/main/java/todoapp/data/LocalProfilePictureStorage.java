package todoapp.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import todoapp.core.user.domain.ProfilePictureException.ProfilePictureLoadFailedException;
import todoapp.core.user.domain.ProfilePictureException.ProfilePictureSaveException;
import todoapp.core.user.domain.ProfilePictureStorage;

import java.io.FileNotFoundException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.UUID;

/**
 * 로컬 디스크에 사용자 프로필 이미지를 저장하고, 불러오는 {@link ProfilePictureStorage} 구현체이다.
 *
 * @author springrunner.kr@gmail.com
 */
@Component
class LocalProfilePictureStorage implements ProfilePictureStorage, ResourceLoaderAware, InitializingBean {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private ResourceLoader resourceLoader;
    private Path basePath;

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Value("${site.user.profilePicture.basePath:./files/user-profile-picture}")
    public void setBasePath(Path basePath) {
        this.basePath = basePath;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Objects.requireNonNull(resourceLoader, "resourceLoader is required");
        if (!Objects.requireNonNull(basePath, "basePath is required").toFile().exists()) {
            basePath.toFile().mkdirs();
            log.debug("create a directory: {}", basePath.toAbsolutePath().toUri());
        }
    }

    @Override
    public URI save(Resource resource) {
        try {
            var profilePicture = basePath.resolve(UUID.randomUUID().toString());
            FileCopyUtils.copy(resource.getInputStream(), Files.newOutputStream(profilePicture));
            return profilePicture.toUri();
        } catch (Exception error) {
            throw new ProfilePictureSaveException("failed to save profile picture to local disk", error);
        }
    }

    @Override
    public Resource load(URI uri) {
        try {
            var resource = resourceLoader.getResource(uri.toString());
            if (!resource.exists()) {
                throw new FileNotFoundException(String.format("not found file for uri: %s", uri));
            }
            return resource;
        } catch (Exception error) {
            throw new ProfilePictureLoadFailedException("failed to load profile picture to local disk", error);
        }
    }

}
