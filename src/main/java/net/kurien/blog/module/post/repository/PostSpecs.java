package net.kurien.blog.module.post.repository;

import net.kurien.blog.entity.Post;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class PostSpecs {
    public static Specification<Post> isManage(String manageYn) {
        return ((root, query, builder) -> {
            if (manageYn.equals("Y")) {
                return null;
            }

            return builder.and(
                    builder.equal(root.get("postView"), 1),
                    builder.or(
                            builder.equal(root.get("postPublish"), 0),
                            builder.and(
                                    builder.equal(root.get("postPublish"), 1),
                                    builder.greaterThanOrEqualTo(root.get("postReservationTime"), new Date().getTime())
                            )
                    )
            );
        });
    }

    public static Specification<Post> withCategory(String category) {
        return ((root, query, builder) ->
                builder.equal(root.get("category"), category)
        );
    }

    public static Specification<Post> likeSubject(String subject) {

        return ((root, query, builder) ->
                builder.like(root.get("subject"), "%" + subject + "%")
        );
    }

    public static Specification<Post> likeAuthor(String author) {
        return ((root, query, builder) ->
                builder.like(root.get("author"), "%" + author + "%")
        );
    }
}
