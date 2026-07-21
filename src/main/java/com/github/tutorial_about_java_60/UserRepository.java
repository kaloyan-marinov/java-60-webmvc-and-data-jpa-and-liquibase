package com.github.tutorial_about_java_60;

import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
/*
Spring automatically implements this repository interface
in a Bean that has the same name (with a change in the case - it is called `userRepository`).
*/
public interface UserRepository extends CrudRepository<User, Integer> {
    
}
