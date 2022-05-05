package com.example.gql;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import io.smallrye.mutiny.converters.uni.UniReactorConverters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@DgsComponent
class PostDataFetcher {

    private final PostRepository posts;

    PostDataFetcher(PostRepository posts) {
        this.posts = posts;
    }

    @DgsQuery
    public Flux<Post> posts() {
        return posts.findAll().convert().with(UniReactorConverters.toFlux()).flatMap(Flux::fromIterable);
    }

    @DgsMutation
    public Mono<Post> createPost(@InputArgument String title, @InputArgument String content) {
        return posts.save(new Post(title, content)).convert().with(UniReactorConverters.toMono());
    }

//    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<?> all() {
//        return ok().body(this.posts.findAll());
//    }
//
//    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
//    public Uni<ResponseEntity<?>> create(@RequestBody CreatePostCommand data) {
//        return this.posts.save(
//                        new Post(data.getTitle(), data.getContent())
//                )
//                .map(p -> created(URI.create("/posts/" + p.getId())).build());
//    }
//
//    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public Uni<ResponseEntity<Post>> get(@PathVariable UUID id) {
//        return this.posts.findById(id)
//                .map(post -> ok().body(post));
//    }
//
//    @PutMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
//    public Uni<ResponseEntity<?>> update(@PathVariable UUID id, @RequestBody UpdatePostCommand data) {
//
//        return Uni.combine().all()
//                .unis(
//                        this.posts.findById(id),
//                        Uni.createFrom().item(data)
//                )
//                .combinedWith((p, d) -> {
//                    p.setTitle(d.getTitle());
//                    p.setContent(d.getContent());
//                    return p;
//                })
//                .flatMap(this.posts::save)
//                .map(post -> noContent().build());
//    }
//
//    @DeleteMapping("{id}")
//    public Uni<ResponseEntity<?>> delete(@PathVariable UUID id) {
//        return this.posts.deleteById(id).map(d -> noContent().build());
//    }
}