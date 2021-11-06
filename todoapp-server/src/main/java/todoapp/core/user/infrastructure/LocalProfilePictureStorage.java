package todoapp.core.user.infrastructure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import todoapp.core.user.domain.FailedLoadingProfilePictureException;
import todoapp.core.user.domain.FailedSavingProfilePictureException;
import todoapp.core.user.domain.ProfilePictureStorage;

import java.io.FileNotFoundException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.UUID;

/**
 * 로컬 저장소 기반으로 사용자 프로필 이미지를 저장하고, 불러오는 {@link ProfilePictureStorage} 구현체
 *
 * @author springrunner.kr@gmail.com
 */
@Component
public class LocalProfilePictureStorage implements ProfilePictureStorage, ResourceLoaderAware, InitializingBean {

    private final Logger log = LoggerFactory.getLogger(LocalProfilePictureStorage.class);
    
    private ResourceLoader resourceLoader;
    private Path basePath;

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Value("${site.userProfilePicture.basePath:./files/user-profile-picture}")
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
            Path profilePicture = basePath.resolve(UUID.randomUUID().toString());
            FileCopyUtils.copy(resource.getInputStream(), Files.newOutputStream(profilePicture));
            return profilePicture.toUri();
        } catch (Exception error) {
            throw new FailedSavingProfilePictureException("로컬 저장소에 프로필 이미지 저장 중 오류가 발생했습니다.", error);
        }
    }

    @Override
    public Resource load(URI uri) {
        try {
            Resource resource = resourceLoader.getResource(uri.toString());
            if (!resource.exists()) {
                throw new FileNotFoundException(String.format("not found file for uri: %s", uri));
            }
            return resource;
        } catch (Exception error) {
            throw new FailedLoadingProfilePictureException("로컬 저장소에서 프로필 이미지 불러오기 중 오류가 발생했습니다.", error);
        }
    }

}
