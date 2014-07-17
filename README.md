
Setup
-----

1. git clone git@github.com:JetBrains/intellij-community.git
2. go into cloned dir and call 'ant'
3. From this repository import the existing projects into eclipse.
4. Configure the ClassPathVariable 'INTELLIJ_IDEA' to point to the checked-out git repository
5. Configure the the string substitution variable 'INTELLIJ_IDEA' to point to the checked-out git repository
6. Run or Debug 'Launch IntelliJ.launch'

Testing
-----

1. Run or Debug ‘Test Intellij.launch’
2. Clean ${INTELLIJ_IDEA}/test-system to remove indexes and caches
