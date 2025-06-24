package alumnithon.skilllink.domain.learning.sharedLearning.service;

import alumnithon.skilllink.domain.learning.sharedLearning.dto.TagsToContentDTO;
import alumnithon.skilllink.domain.learning.sharedLearning.model.ContentTag;
import alumnithon.skilllink.domain.learning.sharedLearning.model.ContentType;
import alumnithon.skilllink.domain.learning.sharedLearning.model.Tag;
import alumnithon.skilllink.domain.learning.sharedLearning.repository.ContentTagRepository;
import alumnithon.skilllink.domain.learning.sharedLearning.repository.TagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContentTagService {
    private final TagRepository tagRepository;
    private final ContentTagRepository contentTagRepository;

    public ContentTagService(TagRepository tagRepository, ContentTagRepository contentTagRepository) {
        this.tagRepository = tagRepository;
        this.contentTagRepository = contentTagRepository;
    }

    @Transactional
    public void addTagsToContent(TagsToContentDTO tagsDTO) {
        for (String originalName : tagsDTO.tagsName()) {
            // Limpiar y normalizar: quitar espacios y pasar a mayúsculas para comparar
            String normalizedName = originalName.trim().toUpperCase();

            // Buscar por nombre en mayúscula
            Optional<Tag> existingTagOpt = tagRepository.findAll().stream()
                    .filter(tag -> tag.getName().trim().toUpperCase().equals(normalizedName))
                    .findFirst();

            // Si no existe, crear uno con formato "Título Mayuscula"
            Tag tag = existingTagOpt.orElseGet(() -> {
                String formattedName = originalName.trim().toUpperCase();
                return tagRepository.save(new Tag(null, formattedName));
            });

            // Verificar si ya está relacionado
            boolean exists = contentTagRepository.existsByContentTypeAndContentIdAndTag_Id(
                    tagsDTO.contentType(), tagsDTO.contentId(), tag.getId()
            );

            if (!exists) {
                ContentTag contentTag = new ContentTag(
                        tagsDTO.contentType(),
                        tagsDTO.contentId(),
                        tag
                );
                contentTagRepository.save(contentTag);
            }
        }
    }

    public List<String> getTagNamesByContent(ContentType contentType, Long contentId) {
        return contentTagRepository
                .findByContentTypeAndContentId(contentType, contentId)
                .stream()
                .map(ct -> ct.getTag().getName())
                .collect(Collectors.toList());
    }
}
