# Use DGS from netflix

## Compile
```bash
mvn compile
```

## Run
```bash
mvn spring-boot:run
```

## Visit

http://localhost:8080/graphiql

## GraphQL
```graphql
mutation {
  createPost(title: "Hello", content: "content") {
    id
    title
    content
    createdAt
  }
}

query Posts {
    posts {
        id
        title
        content
        createdAt
    }
}


mutation {
    updatePost(id: "e66055d5-7eaa-42f0-8bac-c0e4e0767997", title: "Hello", content: "content") {
        id
        title
        content
        createdAt
    }
}

mutation {
    deletePost(id: "cfd8f56a-b7ca-4016-a700-f2ded5012c55")
}

```

## See also
https://github.com/hantsy/spring-puzzles/tree/master/hibernate-reactive-mutiny

https://smallrye.io/smallrye-mutiny/guides/converters
