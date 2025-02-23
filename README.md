# V-Swing

[![pt-br](https://img.shields.io/badge/lang-pt--br-green.svg)](https://github.com/Vinokaa/V-Swing/blob/main/README.pt-br.md)

An UI App used to draw and export drawings to be used in other UI Apps (That use Swing).

<br>

# Inserting a Background Image as Reference

Select the option *Selecionar Imagem* in the upper left corner and select the image that you want as background reference.

![V-swing-1](https://github.com/user-attachments/assets/835d9a6f-ff09-4d9a-b1dc-eef49ed31abc)

<br>

# Drawing

![V-swing-4](https://github.com/user-attachments/assets/9be5595c-6dcd-4359-a22a-ec1e27fb82ba)

At the moment, V-Swing has 4 options in its left bar, them being 1 editing tool for the already drawn shapes and 3 geometric shapes for the user to draw. The description of usage of each tool is written below, following the order of the left bar from the top to the bottom:

1. Editing Tool
   - After selecting this option, the borders of every drawn shape will be shown and the user can select one of them pressing _Left Mouse Click_ over it. Once the shape's border turns red, the user can edit it following the instructions below. If there are shapes overlapping and the user wants to choose one of them, click at the center of the shape wanted;
   - With a shape selected, the user can delete it by pressing _Del_;
   - With a shape selected, the user can move it by pressing _Left Mouse Click_ and dragging the mouse over the drawing board;
   - Pressing _Esc_ with this option selected will move back to the standard mode, hiding the shapes' border from the screen;
<br>

2. Polygon, a shape with many points connected by lines
   - Pressing _Left Mouse Click_ while _Polygon_ is selected will set the spot where the mouse is at as a point of the _Polygon_;
   - Pressing _Enter_ while _Polygon_ is selected finishes the polygon's drawing, connecting the last point with the first one;
   - Pressing _Esc_ while _Polígono_ is selected cancels the polygon's drawing, removing all the selected points;
<br>

3. Ellipse, a shape to draw circular shapes
   - With _Ellipse_ selected, the user needs to press _Left Mouse Click_ at the 4 extremities of where they want to position the _Ellipse_, in any order they want;
<br>

4. Line, similar to the Polygon, but doesn't connect the last point with the first one at the end
   - Pressing _Left Mouse Click_ while _Line_ is selected will set the the spot where the mouse is to a point of the line;
   - After pressing _Left Mouse Click_ once, pressing _Left Mouse Click_ again will continue the line from the last point;
   - Pressing _Esc_ while _Line_ is selected cancels the line's drawing, removing all the selected points;
<br>

# Exporting Your Drawing

After finishing drawing, the user can select the option _Exportar_ at the upper left of the screen.

![V-swing-3](https://github.com/user-attachments/assets/ddb05466-ce59-419f-870b-54d56802ec56)

After selecting the option _Exportar_, the user is prompted about the file name and the output file extension. A description about these extension types are below:

1. Text File (.txt)
   - Tends to be smaller in size when compared to the Object type, but is slower to load when imported into other projects;
   - Every line follows the pattern "Shape's name{Data needed to recreate the shape}", e.g. Polygon{[483,481][503,487][539,489][557,501]} is a Polygon with the starting point at coordinates 483, 481, followed by points at 503, 487 and 539, 589, ending at coordinates 483, 481;
<br>

2. Object (.ser)
   - Uses the standard Java Serializable interface to export the Shape object and its attributes at the moment when it's exported;
   - Tends to be bigger in size when compared to the Text File, but it's faster to load when imported into other projects;
<br>

# Importing Your Drawing into Other Projects

In order to import a drawing made in V-Swing, the use must use the DesenhoImporter class, present in the Importer package.

![V-swing-5](https://github.com/user-attachments/assets/5a036ade-0e9c-4a97-8b28-da89b38dc961)

The DesenhoImporter class needs 5 parameters in its constructor:
  - The X coordinate of the upper left corner of where the drawing will be in your new project;
  - The Y coordinate of the upper left corner of where the drawing will be in your new project;
  - The width of the drawing in your new project;
  - The height of the drawing in your new project;
  - A File object (java.io.File) pointing to the path of the .txt or .ser file of the previously exported drawing;
<br>

The DesenhoImporter Class is a subclass of JComponent, meaning that, after creating it, the user can use it as a normal JComponent from Swing's Java library in your project. The DesenhoImporter class will make all the needed adjustments and add your drawing made in V-Swing to your project.
<br>

# Ending Notes

This project was made individually by me as the final project of the Object-Oriented Programming class (originally Programação Orientada a Objetos).
<br><br>

It was requested that the project used the Swing library to create an application with user interface, so I decided to create V-Swing (Visual Swing) as an UI application that makes creating drawings in the Swing library easier, and with that, minimize coding and making drawing more like it's done in reality, drawing shapes.
<br><br>

I believe that this was one of my biggest projects up until now, and I intend to keep updating it with bug fixes and new tools that ended up being left out for not being the focus of the project.
