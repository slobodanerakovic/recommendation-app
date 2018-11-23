# recommendation-app : April, 2017
## Commercial recommendation system for customers, based on different criteria


**REQUIREMENT** <br />

### Task introduction

Recommendations are very popular in last years, you can find them in ecommerce platforms,
app stores, online movie rentals and many other places. Good recommendation system may
take business to the next level.
<br /><br />
Providing good recommendations may be difficult, because every single user is different. There
are many popular strategies for generating recommendations, for example:
<br /><br />
- Random with totally random items
- Top-N with most popular items in the context of viewing, commenting, selling
- Collaborative Filtering with items calculated for user preferences, based on preferences of
similar users
<br /><br />
Each strategy needs probably different storage. Top-N may store data in sets or lists, but for
Collaborative Filtering graphs databases will be better option.

### Task details

Implement RESTful API for generating recommendations in the ecommerce application.
<br />
Requirements:
<br />
- API should allow to collect interactions between users and products.
- Typical interactions in ecommerce are: VIEWED, COMMENTED, BOUGHT. But...
Maybe BOUGHT interaction is more important in the context of recommendation than
VIEWED?
- Users and products may be identified with UUIDs or different IDs
- API should allow to block/restore some products, e.g. when they are out of stock
- API should allow to provide list of recommendations for the users
- Length of the list should be parameterized
- Recommendation strategy should also be parametrized (Random, Top-N, etc.)
- Try to implement as many strategies as you can
<br /><br />
Recommendation API doesn’t have to store information related to product details. In
production system, these data will be fetched from different microservice.
<br /><br />
As you see, some strategies may provide real problems... For example, Top-N strategy may
return products which were very popular... 4 years ago. Try to find solution for this problem.
<br /><br />**END REQUIREMENT**

## Techinical overview:
 - The application is spring-boot microservice, 1.5.2.RELEASE version. 
 - Java 8 is used for development
 - Technologies: Spring, JPA, JMS, Quartz, AOP, Jersey(Javax-Ws)Maven, Docker
 - Postgres database
 <br /><br />

**NOTE:** Docker deployment process has been tested on Ubuntu 16.04 from local docker repository scratch.<br />
**init.sh** - is the entry point containing instruction for:
1. database docker container/image build and run
2. application docker container/image build and run
<br /><br />

The entire process shall be started by simply executing script **./init.sh** (set execution mode if necessarily "chmod 755 init.sh" before execution.<br />
One of the main issue which could be is database running Ip. Since docker has various strategy for assigning ip adress to its container, and the spring boot, which this application is, requires in the file **recommendation-app/src/main/resources/application.yml** contains valid url for application database driver:    <br />     
    **url: jdbc:postgresql://localhost/recommendation**
 <br /><br />
However, the **init.sh** script does this ip detection and its change on its own, but, I am just stressing if any issue about it.
If you are running this on windows (I guess not) then application database driver url in the file **recommendation-app/src/main/resources/application.yml** needs to be updated manually, and docker build/run needs to be executed manually from the init.sh file.

## THE APPLICATION
### KEY POINTS

1. Since this is backend application, with exposed APIs for requested  requirements, it does not have any interface, but use JUnits for purpose of testing the APIs (check test suite  com.recommendation.test.RecommendationJUnitTestSuite as entry points for junit tests).
This is run during "mvn package" maven phase.<br />
Also, it can be used via web browser or curl get/post calls like:
http://localhost:6789/recommendation-app/api/products/block/all/?adminid=DDfDpQ%3D%3D (where DDfDpQ%3D%3D represented encrypted id of the potential admin privileged user - more about this, in the comments)

2. There are some testing data which populate database on start, used by these JUnit tests in the application (you can simulate also those requests on your own based on the business logic and data

3. I put a lot of comments all over classes to provide better overview and description why something is done, how it works and which business logic it represents.

4. com.recommendationjob.ProductPopularityUpdatedJob is key class for solution described in the requirement: How to apply Top-N strategy, to not return stale date (4 years ago popular product). This is actually solved by specifying date range to which we want to check which products were popular. Nonetheless, in the comments at key places, this is thoroughly described.
