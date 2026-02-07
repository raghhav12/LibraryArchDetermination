## Objective

This program scans a user-selected folder and identifies the CPU architecture
of ELF (Executable and Linkable format) shared libraries (.so files). It outputs the total number 
of valid libraries and lists file names along with their architecture type.

## Algorithm Overview

1. User selects folder using Android Storage Access Framework (SAF)
2. App recursively traverses the directory
3. Only .so files are processed
4. First 20 bytes of each file are read
5. ELF header is parsed:
    - Magic number validation
    - Endianness detection
    - Machine architecture extraction
6. Architecture is mapped to:
   armeabi-v7a, arm64-v8a, x86, x86-64, mips
7. Results are formatted and displayed

## Output Format

Total number of libraries: N
File name-> libexample.so || Arch type -> arm64-v8a

## I have used the following as references:

1. The ELF specification was used to understand the structure of ELF
headers required to solve the assignment. As I didn't had a prior 
knowledge of ELF format so the document was used as a technical reference.
    https://refspecs.linuxfoundation.org/elf/elf.pdf

2. The Android Storage Access Framework documentation was used to implement 
user-selected folder access and safe file reading on Android.
    https://developer.android.com/guide/topics/providers/document-provider
