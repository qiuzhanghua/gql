package com.example.gql;

import org.springframework.stereotype.Component;
import io.smallrye.mutiny.Uni;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import java.util.logging.Logger;
import org.hibernate.reactive.mutiny.Mutiny;
@Component
public class PostRepository {
    private static final Logger LOGGER = Logger.getLogger(PostRepository.class.getName());

    private final Mutiny.SessionFactory sessionFactory;

    public PostRepository(Mutiny.SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Uni<List<Post>> findAll() {
        CriteriaBuilder cb = this.sessionFactory.getCriteriaBuilder();
        // create query
        CriteriaQuery<Post> query = cb.createQuery(Post.class);
        // set the root class
        Root<Post> root = query.from(Post.class);
        return this.sessionFactory.withSession(session -> session.createQuery(query).getResultList());
    }

    public Uni<List<Post>> findByKeyword(String q, int offset, int limit) {

        CriteriaBuilder cb = this.sessionFactory.getCriteriaBuilder();
        // create query
        CriteriaQuery<Post> query = cb.createQuery(Post.class);
        // set the root class
        Root<Post> root = query.from(Post.class);

        // if keyword is provided
        if (q != null && !q.trim().isEmpty()) {
            query.where(
                    cb.or(
                            cb.like(root.get(com.example.gql.Post_.title), "%" + q + "%"),
                            cb.like(root.get(com.example.gql.Post_.content), "%" + q + "%")
                    )
            );
        }
        //perform query
        return this.sessionFactory.withSession(session -> session.createQuery(query)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList());
    }


    public Uni<Post> findById(UUID id) {
        Objects.requireNonNull(id, "id can not be null");
        return this.sessionFactory.withSession(session -> session.find(Post.class, id))
                .onItem().ifNull().failWith(() -> new PostNotFoundException(id));
    }

    public Uni<Post> save(Post post) {
        if (post.getId() == null) {
            return this.sessionFactory.withSession(session ->
                    session.persist(post)
                            .chain(session::flush)
                            .replaceWith(post)
            );
        } else {
            return this.sessionFactory.withSession(session -> session.merge(post).onItem().call(session::flush).replaceWith(post));
        }
    }

    public Uni<Post> update(Post post) {
        return this.sessionFactory.withSession(session -> session.merge(post).onItem().call(session::flush).replaceWith(post));
    }


    public Uni<Post[]> saveAll(List<Post> data) {
        Post[] array = data.toArray(new Post[0]);
        return this.sessionFactory.withSession(session -> {
            session.persistAll(array);
            session.flush();
            return Uni.createFrom().item(array);
        });
    }

//    @Transactional
//    public Uni<Post> updatePost(UUID id, Post post) {
//        this.sessionFactory.withSession(session -> session.find(Post.class, id))
//                .onItem().ifNull().failWith(() -> new PostNotFoundException(id));
//    }

    public Uni<Integer> deleteById(UUID id) {
        CriteriaBuilder cb = this.sessionFactory.getCriteriaBuilder();
        // create delete
        CriteriaDelete<Post> delete = cb.createCriteriaDelete(Post.class);
        // set the root class
        Root<Post> root = delete.from(Post.class);
        // set where clause
        delete.where(cb.equal(root.get(com.example.gql.Post_.id), id));
        // perform update
        return this.sessionFactory.withTransaction((session, tx) ->
                session.createQuery(delete).executeUpdate()
        );
    }

    public Uni<Integer> deleteAll() {
        CriteriaBuilder cb = this.sessionFactory.getCriteriaBuilder();
        // create delete
        CriteriaDelete<Post> delete = cb.createCriteriaDelete(Post.class);
        // set the root class
        Root<Post> root = delete.from(Post.class);
        // perform update
        return this.sessionFactory.withTransaction((session, tx) ->
                session.createQuery(delete).executeUpdate()
        );
    }

}