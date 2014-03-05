vop-2014-team12
===============
# Stambomen team 12 #

## Preamble ##
Stambomen is an application that's splitted into two applications, the stambomenWebAPP and the StambomenDesktopApp.


## Repository folders ##
###1.[StambomenClient][sc]</br> ###
StambomenClient folder contains the Client for the destktop application and the webapplication.
#### packages: ####
Source Packages:

* default package
  * contains the main startup class
* dto
  * contains the DTO files
* service
  * contains the service files and the controllers
* util
 * PersonUtil

Test Packages:

 * com.mycompany.stambomenclient
  * appTest

### 2.[StambomenDesktopApp][sda] ###
StambomenDesktopApp folder contains the deskAPP to edit the familytree's.
#### packages: ####
Source Packages:

 * default package
  * contains the main startup class
 * gui
  * contains the GUI panels
 * gui.controller
  * contains the GUI controller classes
 * gui.controls
  * contains custom build controls
 * gui.controls.listeners
   * contains the interface listener files

### 3.[StambomenWebAPI][swAPI]###
StambomenWebAPI folder contains the actual webservice with the DB-connection.
#### packages: ####
Source Packages:

 * domain
  * contains the object classes 
 * domain.controller
  * contains the controller classes for user, tree and person
 * exception
  * contains the custom exceptions
 * message
  * contains message files
 * persistence
  * contains the persistencemanager and the DAO's
 * service
  * contains the services and authentication
 * util
  * StringValidation

Test Packages:

 * mocks
  * contains the test classes


### 4.[StambomenWebApp][sc]###
StambomenWebApp folder contains the webapplication of stambomen
#### packages & folders: ####
Web Pages:

 * WEB-INF
  * web.xml
 * images
 * css
 * js
 * jsp pages for login, main and others

Source Packages:

 * filter
  * loginFilter
 * servlet
  * contains the servlets for the user, tree and friend


### 5.[Stambomen][s]##
The stambomen folder contains the [DB-script][dbS] and the [perlscript][pS]







[sc]: https://github.ugent.be/iii-vop/vop-2014-team12/tree/master/StambomenClient)StambomenClien
[sda]: https://github.ugent.be/iii-vop/vop-2014-team12/tree/master/StambomenDesktopApp
[swAPI]: https://github.ugent.be/iii-vop/vop-2014-team12/tree/master/StambomenWebAPI
[swAPP]: https://github.ugent.be/iii-vop/vop-2014-team12/tree/master/StambomenWebApp
[s]: https://github.ugent.be/iii-vop/vop-2014-team12/tree/master/Stambomen
[dbS]:https://github.ugent.be/iii-vop/vop-2014-team12/tree/master/Stambomen/DBFile
[pS]:https://github.ugent.be/iii-vop/vop-2014-team12/tree/master/Stambomen/perl
