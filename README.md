# Swagger composer

This tool lets you to merge a few swagger spec files into one.

Why? Because swagger 2.0 spec does not support sufficient modularity.

# Usage

The input swagger specs must follow a convention:

 * There is exactly one **master** `swagger.json` **into** which others are merged.
 * Any number of **partials**, whose elements are to be merged.

## Merge specification

 * All input files are valid swagger files alone
 * Only **paths, definitions, parameters and responses** are copied from partials.
 * If element is defined only in one partial, it is copied as is.
 * If element exists in more than one partial, then:
   * if master already contains the element, masters version is used
   * if all definitions are equal, they are copied to output
   * if any definition is not equal to other, merge fails
 * `basePath` is pre-appended to all paths in partials before merging
 * `basePath` is not allowed in master file
