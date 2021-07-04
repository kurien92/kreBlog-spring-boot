package net.kurien.blog.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_parent_id")
    private Category categoryParent;

    private Long categoryDepth;

    private Long categoryOrder;

    @Column(name = "category_key")
    private String categoryKey;

    private String categoryName;

    @OneToMany(mappedBy = "categoryParent")
    private List<Category> children = new ArrayList<>();

    @Builder
    public Category(Category categoryParent, Long categoryDepth, Long categoryOrder, String categoryKey, String categoryName, List<Category> children) {
        this.categoryParent = categoryParent;
        this.categoryDepth = categoryDepth;
        this.categoryOrder = categoryOrder;
        this.categoryKey = categoryKey;
        this.categoryName = categoryName;
        this.children = children;
    }
}
