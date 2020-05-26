## Image Labeling System

### Simplified architecture
![Image Labeling system simple diagram](image-labeling-diagrams.png)

### Usage description
#### Creating a new images domain
- In the device the user fills the form for imagem domain creation, e.g. 'cats and dogs';
- The device send the __image domain__ object to the Ruby API, which will use it to use to store related imagens later;
- Once the domain is created, the user may see it on domains screen of the app;
- Accessing the domain, the user can now create the classes for this domain, if it's not already created;
- Once the classes are created, the API will run a background job to request the image URLs of this classes, requesting it from the __Google Images API__ and save them in the database;
- The user can now select a class of the refered domain to put the label. If there's already some URL image in the API for the user to classify, he will see the images in a screen with two buttons:
  - Discard: which will discard this image if it does not belong to the class the user is labeling;
  - OK: which marks the image with the label of the class being labeled.
- Once the user is done, he can simply press return. 
