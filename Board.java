import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;

public class Board {

  private int boardX1,boardX2,boardX3,boardX4,boardY1,boardY2,boardY3,boardY4,boardY5,boardY6,boardY7,boardY8,boardY9;

  private int x1Choose,x2Choose,x3Choose,x4Choose,y1Choose,y2Choose;

  private int x1Check,x2Check,x3Check,x4Check,y1Check,y2Check,y3Check,y4Check,y5Check,y6Check,y7Check,y8Check,y9Check;

  private int x1Result, x2Result,x3Result,x4Result,y1Result;

  private Color VERY_LIGHT_RED;
  private Color VERY_LIGHT_BLUE;
  private Color DARK_GREEN;
  private Color DARK_YELLOW;
  private Color GREY;
  private Color LIGHT_BROWN;
  private Color PURPLE;
  private Color VERY_DARK_BLUE;
  //changed to plannets instead of colors
  private BufferedImage imgOneB;
  private BufferedImage imgTwoB;
  private BufferedImage imgThreeB;
  private BufferedImage imgFourB;
  private BufferedImage imgFiveB;
  private BufferedImage imgSixB;
  private BufferedImage imgSevenB;
  private BufferedImage imgEightB;

  private File imgOne = new File("1.png");
  private File imgTwo = new File("2.png");
  private File imgThree = new File("3.png");
  private File imgFour = new File("4.png");
  private File imgFive = new File("5.png");
  private File imgSix = new File("6.png");
  private File imgSeven = new File("7.png");
  private File imgEight = new File("8.png");



  public Board()
  {
    //board
    this.boardX1 = 300;this.boardX2 = 340;this.boardX3 = 380;this.boardX4 = 420;this.boardY1 = 100;this.boardY2 = 150;this.boardY3 = 200;this.boardY4 = 250;this.boardY5 = 300;this.boardY6 = 350;this.boardY7 = 400;this.boardY8 = 450;
    this.boardY9=500;
    //choose
    this.x1Choose = 50;this.x2Choose = 100;this.x3Choose=150;this.x4Choose = 200;this.y1Choose = 150;this.y2Choose = 200;
    //checks
    this.x1Check = 450;this.x2Check = 475;this.x3Check = 500;this.x4Check = 525;this.y1Check = 100;this.y2Check = 150;this.y3Check = 200;this.y4Check = 250;this.y5Check = 300;this.y6Check = 350;this.y7Check = 400;this.y8Check = 450;
    this.y9Check=500;
    //results
    this.x1Result =290; this.x2Result=334;this.x3Result=378;this.x4Result=422;
    this.y1Result = 30;
    //colors
    this.VERY_LIGHT_RED= new Color(255,102,102);
    this.VERY_LIGHT_BLUE= new Color(51,204,255);
    this.DARK_GREEN =new Color(0,153,0);
    this.DARK_YELLOW= new Color(255,204,0);
    this.GREY = new Color(153,153,153);
    this.LIGHT_BROWN = new Color(153,102,0);
    this.PURPLE = new Color(102,0,153);
    this.VERY_DARK_BLUE = new Color(0,0,153);
  }


 
  public List<List<Integer>> getPlayList()
  {
    List<List<Integer>> arr = new ArrayList<List<Integer>>();
    List<Integer> listYBoard = new ArrayList<>();
    List<Integer> listXBoard = new ArrayList<>();
    Collections.addAll(listYBoard, boardY9,boardY8,boardY7,boardY6,boardY5,boardY4,boardY3,boardY2,boardY1);
    Collections.addAll(listXBoard, boardX1,boardX2,boardX3,boardX4);
    for(int i=0; i<listYBoard.size(); i++)
    {
      for(int j = 0; j<listXBoard.size();j++)
      {
        List<Integer> row = new ArrayList<Integer>();
        row.add(listXBoard.get(j));
        row.add(listYBoard.get(i));
        arr.add(row);
      }
    }
    return arr;
  }

  public List<List<Integer>> getChooseList()
  {
    List<List<Integer>> arr = new ArrayList<>();
    List<Integer> listXBoard = new ArrayList<>();
    List<Integer> listYBoard = new ArrayList<>();
    Collections.addAll(listXBoard, x1Choose,x2Choose,x3Choose,x4Choose);
    Collections.addAll(listYBoard, y1Choose,y2Choose);
    for(int i = 0;i<listYBoard.size();i++)
    {
      for(int j = 0;j<listXBoard.size();j++)
      {
        List<Integer> row = new ArrayList<>();
        row.add(listXBoard.get(j));
        row.add(listYBoard.get(i));
        arr.add(row);
      }
    }
    return arr;
  }

  public List<List<Integer>> getCheckList()
  {
    List<List<Integer>> arr = new ArrayList<>();
    List<Integer> listXBoard = new ArrayList<>();
    List<Integer> listYBoard = new ArrayList<>();
    Collections.addAll(listXBoard, x1Check,x2Check,x3Check,x4Check);
    Collections.addAll(listYBoard, y9Check,y8Check,y7Check,y6Check,y5Check,y4Check,y3Check,y2Check,y1Check);
    for(int i: listYBoard)
    {
      for(int j: listXBoard)
      {
        List<Integer> row = new ArrayList<>();
        row.add(j);
        row.add(i);
        arr.add(row);
      }
    }
    return arr;
  }

  public List<List<Integer>> getResultList()
  {
    List<List<Integer>> arr = new ArrayList<>();
    List<Integer> listXBoard = new ArrayList<>();
    List<Integer> listYBoard = new ArrayList<>();
    Collections.addAll(listXBoard, x1Result,x2Result,x3Result,x4Result);
    Collections.addAll(listYBoard, y1Result);
    for(int i: listYBoard)
    {
      for(int j: listXBoard)
      {
        List<Integer> row = new ArrayList<>();
        row.add(j);
        row.add(i);
        arr.add(row);
      }
    }
    return arr;
  }
  public List<Color> getColor()
  {
    List<Color> arr = new ArrayList<>();
    Collections.addAll(arr, VERY_LIGHT_RED,VERY_LIGHT_BLUE,DARK_GREEN,DARK_YELLOW,GREY,LIGHT_BROWN,PURPLE,VERY_DARK_BLUE);
    return arr;
  }
  
  public List<BufferedImage> getPlannets() 
  {
    List<BufferedImage> arr = new ArrayList<>();
    try {
      imgOneB = ImageIO.read(imgOne);
      imgTwoB = ImageIO.read(imgTwo);
      imgThreeB = ImageIO.read(imgThree);
      imgFourB = ImageIO.read(imgFour);
      imgFiveB = ImageIO.read(imgFive);
      imgSixB = ImageIO.read(imgSix);
      imgSevenB = ImageIO.read(imgSeven);
      imgEightB = ImageIO.read(imgEight);
    } catch (IOException e) {
      e.printStackTrace();
    }
    arr.addAll(Arrays.asList(imgOneB,imgTwoB,imgThreeB,imgFourB,imgFiveB,imgSixB,imgSevenB,imgEightB));
    return arr;
  }
}
