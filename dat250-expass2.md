# Creating the models
I started off by creating a model for each of the domain objects.
I included all the properties detailed in the model and i made the user object have a list of 
created Polls and Votes
I made a Poll have a list of VoteOptions
I made Vote have one selected VoteOption.
I also made every created entity have a unique id

# Creating the controllers
I only managed to create enough controller methods for the flow in task 3.
I also got the flow working by sending curl requests but i did not manage to get it 
to work using the RestClient Spring library.

# Problems
I encountered several problems during the project. The most interesting ones were:

# JSON serialization when creating a poll:
At first i did not have any Json ignores in any of my models. That led to an infinite loop
when returning a ResponseEntity with the body of a poll to avoid this i used @JsonIgnore

# Managing ids:
During my first try at this project i could not find a good way to identify the different
objects once they were created, i therefore this time tried to make it so that every 
object was assigned a unique id in their constructor. This did not work out as well as i would have hoped
and the better solution would probably have been to assign the id in the DomainManager when the objects were being stored.

# Pending issues i did not manage to solve: 

# Testing with Java code:
As i said i got the flow in task 3 to work somewhat using curl and http request, but for some reason i cannot get the API
to work as expected when testing with Java code.

# All controller methods for the different objects
I did not get finished implementing all the different controller methods for the API to be a complete CRUD app. The reason for
this is because i felt like my ideas for the DomainManager and Models were not good enough to do it. Therefore i focused on completing
the other tasks because i felt like i had a better chance at getting them done

# The code 
The code i am describing can be found in the same repo as this markdown file in the main branch. I am currently working on a branch
called v2 because i want to finish the project entirely.

