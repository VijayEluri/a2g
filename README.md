
# Overview


build with 

    gradle clean build

# Internals



# See also



# Testing

A number of APDUs are held in src/test/resources/apdus/ these were created using yaz-client and the following commands

yaz-client opencontent.indexdata.com:210
Z> base wikipedia
Z> find @attr 1=4 science
Z> show 1

a2g should be able to sensibly decode these, and round-tripping the decoded apdus should generate binary arrays that match the yaz encoding.

# Useful links

* http://luca.ntop.org/Teaching/Appunti/asn1.html
