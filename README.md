## Image Labeling System - Android
This is the Android app side of a project created to facilitate the __data acquisition__ and the __data labelling__ steps for supervised computer vision tasks. 

You just need to create an image __Domain__ (e.g. **animal**) and its __Classes__ (e.g. **Dog**, **Cat** and **Duck**) to start the labeling.

### **Step by step usage for labeling**

1. In the first screen, create a new domain by tapping in the *plus* button. Let's call it "animals".
    * This will lead you to Classes creation screen
2. Create new classes, let's call them "cat" and "dog".
3. Tap on the class you want to start labeling (let's do it for "dogs"). This will lead you to the fetch and label screen.
4. In the menu, you will see a search field, tap on the indicated place to insert a search term to find useful images for the dog class. For instance, type "puppy" and hit the search icon.
5. You probably will be able to see an image of a young good boy.
6. If the image you see belongs to the class you are labeling hit "Confirm", if not, hit "Discard".
7. When you think that it's enough for this search term, you can edit it and continue labeling for this class or go back and select another class to label.

### **How to download the images**

### Simplified architecture
![Image Labeling system simple diagram](image-labeling.png)

### Usage description
- In the Android app home screen, you can select a __imagem domain__ or create a new one (e.g. **animal**);
- If you choose to create a new domain:
  - The device send the __image domain__ object to the __Ruby API__ ([Code Here](https://github.com/MaximoDouglas/image-labeling-api));
- By selecting an __image domain__ in the home screen, you will lead to the __image domain__ details screen, in which, you will be able to create a new __image class__ for this __image domain__ or select an existing one;
- If you choose to create a new domain:
  - The device send the __image class__ object to the __Ruby API__ ([Code Here](https://github.com/MaximoDouglas/image-labeling-api));
- By selecting a __image class__, you will be redirected to the image classification screen, where the magic happens;
- In the classification screen, the you will be able to edit the __image class__ details as well as to input an search term that will be used to request images from the RapidAPI API;
- Once you type a search term and hit the search button (this can be done at anytime you want), the app will request images from the RapidAPI that matches this search term;
- The images will be shown in the screen together with two buttons:
  - Dicard, that will do nothing besides passing to the next image;
  - Confirm, that will confirme that the shown image belongs to the __image class__ you are working on - which means that the app will send to the __Ruby API__ ([Code Here](https://github.com/MaximoDouglas/image-labeling-api)) an Image object that contains the image URL and its label;
- Once the labeling task is complete for this class, you can now go back to the domain detail screen and select another class to label images with just one click;
- If you have finished labeling for this image domain, you can go back to the home screen and select or create new __image domains__ to label them;
