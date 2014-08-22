
Setup
-----

1. git clone git@github.com:JetBrains/intellij-community.git
2. go into cloned dir and call 'ant'
3. From this repository import the existing projects into eclipse.
4. Configure the ClassPathVariable 'INTELLIJ_IDEA' to point to the checked-out git repository
5. Configure the the string substitution variable 'INTELLIJ_IDEA' to point to the checked-out git repository
6. Configure the the string substitution variable 'INTELLIJ_IDEA_PLUGINS to point to the checked-out org.xtext.example.mydsl.idea git repository

Running/Debugging
-----

* Run or Debug MyDsl Language ’MyDsl Launch IntelliJ.launch’
* Run or Debug Domainmodel Language ’Domainmodel Launch Intellij.launch’
* Run or Debug All Languages ‘All Launch IntelliJ.launch’

Testing
-----

* Run or Debug ‘Test Intellij.launch’
* Clean ${INTELLIJ_IDEA}/test-system to remove indexes and caches
