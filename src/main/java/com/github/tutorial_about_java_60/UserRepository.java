package com.github.tutorial_about_java_60;

import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a «bean definition» called `userRepository`
// CRUD refers Create, Read, Update, Delete
/*
Spring automatically implements this repository interface
in a «bean definition» that has almost the same name as the class
(the only difference will be a change in the case).
In this case, the name will of the «bean definition» be `userRepository`.
*/
public interface UserRepository extends CrudRepository<User, Integer> {
    
}
