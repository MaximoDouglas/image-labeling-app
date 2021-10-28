## Image Labeling System - Android
This is the Android app side of a project created to facilitate the __data acquisition__ and the __data labelling__ steps for supervised computer vision tasks. 

You just need to create an image __Domain__ (e.g. **animal**) and its __Classes__ (e.g. **Dog**, **Cat** and **Duck**) to start the labeling.

### **Step by step usage for labeling**

1. In the first screen, create a new domain by tapping in the *plus* button. Let's call it "animals".
    * This will lead you to Classes creation screen
    * ![Alt Text](docs/1.gif)

2. Create new classes, let's call them "cat" and "dog".
3. Tap on the class you want to start labeling (let's do it for "dogs"). This will lead you to the fetch and label screen.
4. In the menu, you will see a search field, tap on the indicated place to insert a search term to find useful images for the dog class. For instance, type "puppy" and hit the search icon.
5. You probably will be able to see an image of a young good boy.
6. If the image you see belongs to the class you are labeling hit "Confirm", if not, hit "Discard".
7. When you think that it's enough for this search term, you can edit it and continue labeling for this class or go back and select another class to label.

### **How to download the images**

### **Simplified architecture**
![Image Labeling system simple diagram](image-labeling.png)
