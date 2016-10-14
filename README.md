MatchMaker
===============
Pre-requisits
-----------------
Tomcat version 6 or above</br>
Java 1.8</br>
Maven 3</br>
Configuration and Installation (As a Web Application)
-----------------
1) Checkout the Matchmaker repository
~~~
git clone https://github.com/Data-to-Insight-Center/SA-Matchmaker.git
~~~
2) Add Research Object, Repository and People profile templates to the source
~~~
SA-Matchmaker/src/main/resources/edu/indiana/d2i/matchmaker/profile
~~~
Sample profiles templates are already available in the source at https://github.com/Data-to-Insight-Center/SA-Matchmaker/tree/master/src/main/resources/edu/indiana/d2i/matchmaker/profile
~~~
You can define your own profile templates according to your application. Matchmaker will use these templates as a schema at runtime and will expect runtime profiles to be compatible with this schema
~~~
3) Build the module
~~~
cd SA-Matchmaker
mvn clean install -DskipTests
~~~
4) Copy the .war file to tomcat/webapps folder
~~~
cp target/matchmaker-1.0.0.war CATALINA_HOME/webapps/mm.war
~~~
5) Unzip mm.war
~~~
unzip CATALINA_HOME/webapps/mm.war -d CATALINA_HOME/webapps/mm
~~~
6) Configure Matchmaker to retrieve people and repository profiles</br></br>
This can be done in two ways;</br></br>
1. Enable Matchmaker to retrieve profiles from a directory</br>
Copy the Repositories and People profiles to mm/WEB-INF/classes/edu/indiana/d2i/matchmaker/profile/ directory. These are runtime profiles and those should be compatible with the templates added in Step 2. You can override the template files in the 'profile' directory if needed at this stage.
~~~
cp repositories.json person.json CATALINA_HOME/webapps/mm/WEB-INF/classes/edu/indiana/d2i/matchmaker/profile/
~~~
Set parameters in mm/WEB-INF/classes/edu/indiana/d2i/matchmaker/util/matchmaker.properties configuration file to retrieve People and Repository profiles from a directory
~~~
cached.profile.repositories=../profile/repositories.json
cached.profile.person=../profile/person.json
~~~
</br>
2. Enable Matchmaker to retrieve profiles from an external REST API</br>
Set parameters in mm/WEB-INF/classes/edu/indiana/d2i/matchmaker/util/matchmaker.properties configuration file to retrieve People and Repository profiles from an external REST API
~~~
repo.list.rest.url=http://localhost:8082/sead-pdt/repositories
repo.profile.rest.url=http://localhost:8082/sead-pdt/repositories
repo.identifier=orgidentifier
people.list.rest.url=http://localhost:8082/sead-pdt/people/list
people.profile.rest.url=http://localhost:8082/sead-pdt/people
people.identifier=@id
~~~
'repo.list.rest.url' should list the repository profiles as a list of json objects and each json object should have the 'repo.identifier' field which can be used to get full profile information for each repository by appending that identifier to the 'repo.profile.rest.url'.</br>
Similarly 'people.list.rest.url' should list the people profiles as a list of json objects and each json object should have the 'people.identifier' field which can be used to get full profile information for each person by appending that identifier to the 'people.profile.rest.url'.</br>

Please see the following documentation for more details about these REST endpoints;</br>
~~~
https://github.com/Data-to-Insight-Center/PDT/wiki/Repository-Service
https://github.com/Data-to-Insight-Center/PDT/wiki/People-Service
~~~

7) Start tomcat

Use matchmaker - REST API
-----------------
1) Configure and install Matchmaker as a web application
</br></br>
2) Send a POST request to http://&lt;host&gt;:&lt;port&gt;/mm/mm/rest with the following request as the POST body
~~~
{
  "@context": [
    "https://w3id.org/ore/context"
  ],
  "Rights Holder": "https://sead2-beta.ncsa.illinois.edu/api/users/568acec7f26f437b16cafac4",
  "Aggregation": {
    "Uploaded By": "https://sead2-beta.ncsa.illinois.edu/api/users/568acec7f26f437b16cafac4",
    "Title": "test_dataset1 - new-cur-obj-test",
    "Creation Date": "04-03-2016",
    "similarTo": "https://sead2-beta.ncsa.illinois.edu/datasets/56966fc4e4b01d13f5ced4a5",
    "Creator": ["0000-0001-9351-557X"],
    "Abstract": ["Its a simple test"],
    "Identifier": "https://sead2-beta.ncsa.illinois.edu/spaces/curations/56da04d7e4b09d159f672258",
    "Publishing Project": "https://sead2-beta.ncsa.illinois.edu/spaces/56966fa6e4b01d13f5ced4a3",
    "@id": "https://sead2-beta.ncsa.illinois.edu/api/curations/56da04d7e4b09d159f672258/ore#aggregation"
  },
  "Preferences": {
    "Purpose": "Testing-Only",
    "Repository": null
  },
  "Aggregation Statistics": {
    "Data Mimetypes": [
      "image/png",
      "text/plain"
    ],
    "Number of Datasets": 2,
    "Number of Collections": 1,
    "Max Dataset Size": "18317",
    "Total Size": "11000000",
    "Max Collection Depth": "2"
  }
}
~~~
The result would be;
~~~
[
  {
    "orgidentifier": "sda",
    "Per Rule Scores": [
      {
        "Score": 1,
        "Message": "Total size is acceptable (<= 1000000000)",
        "Attribute Type": "Critical",
        "Rule Name": "Maximum Total Size"
      },
      {
        "Score": 1,
        "Message": "Collection depth is acceptable (<= 4)",
        "Attribute Type": "Critical",
        "Rule Name": "Maximum Collection Depth"
      }
    ],
    "Total": 2,
    "repositoryName": "IU SEAD Cloud",
    "Result": "Percentage of total attributes matched : 100.00% | Percentage of critical attributes matched : 100.00%"
  },
  {
    "orgidentifier": "ideals",
    "Per Rule Scores": [
      {
        "Score": 1,
        "Message": "Total size is acceptable (<= 10000000000)",
        "Attribute Type": "Critical",
        "Rule Name": "Maximum Total Size"
      },
      {
        "Score": -1,
        "Message": "Collection depth is not acceptable (<= 1)",
        "Attribute Type": "Critical",
        "Rule Name": "Maximum Collection Depth"
      }
    ],
    "Total": 0,
    "repositoryName": "IDEALS",
    "Result": "Percentage of total attributes matched : 50.00% | Percentage of critical attributes matched : 50.00%"
  },
  {
    "orgidentifier": "bob",
    "Per Rule Scores": [
      {
        "Score": 1,
        "Message": "Total size is acceptable (<= 10000000)",
        "Attribute Type": "Critical",
        "Rule Name": "Maximum Total Size"
      },
      {
        "Score": 1,
        "Message": "Collection depth is acceptable (<= 10)",
        "Attribute Type": "Critical",
        "Rule Name": "Maximum Collection Depth"
      }
    ],
    "Total": 2,
    "repositoryName": "SEAD NDS Labs Publisher (Proof-of-Concept)",
    "Result": "Percentage of total attributes matched : 100.00% | Percentage of critical attributes matched : 100.00%"
  },
  {
    "orgidentifier": "openicpsr",
    "Per Rule Scores": [
      {
        "Score": 1,
        "Message": "Total size is acceptable (<= 10000000000)",
        "Attribute Type": "Critical",
        "Rule Name": "Maximum Total Size"
      },
      {
        "Score": -1,
        "Message": "Collection depth is not acceptable (<= 1)",
        "Attribute Type": "Critical",
        "Rule Name": "Maximum Collection Depth"
      }
    ],
    "Total": 0,
    "repositoryName": "Inter-university Consortium for Political and Social Research",
    "Result": "Percentage of total attributes matched : 50.00% | Percentage of critical attributes matched : 50.00%"
  }
]
~~~

Use matchmaker - Command Line Interface
-----------------
Documentation on Command line executin of the Matchmaker is available at https://github.com/Data-to-Insight-Center/SA-Matchmaker/tree/master/samples. 
</br>

Use matchmaker - Web UI
-----------------
1) Configure and install Matchmaker as a web application
</br></br>
2) Web UI of the Matchmaker is available at http://&lt;host&gt;:&lt;port&gt;/mm/crud-table.html. There you can view the rules, edit rules and send requests with Research Object profiles to get matched repositories. 
</br>
</br>

