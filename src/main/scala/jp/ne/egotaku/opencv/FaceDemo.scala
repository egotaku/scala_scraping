package jp.ne.egotaku.opencv

import org.opencv.core.{Core, MatOfRect, Point, Scalar}
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc
import org.opencv.objdetect.CascadeClassifier
/**
  * Created by takuya_st on 2016/11/21.
  */
object FaceDemo extends App {

  override def main(args: Array[String]): Unit = {
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME)

    //検出器のデータを取得
    val faceDetector = new CascadeClassifier(getClass.getResource("/lbpcascade_frontalface.xml").getPath)
    //サンプル用の画像を読み込む
    val image = Imgcodecs.imread(getClass.getResource("/rin.jpg").getPath)

    //顔検出を行うオブジェクト
    val faceDetections = new MatOfRect()
    faceDetector.detectMultiScale(image, faceDetections)

    println("Detected %s faces".format(faceDetections.toArray.size))
    //画像に顔を囲む四角形を描画？
    faceDetections.toArray.foreach(rect => {
      Imgproc.rectangle(
        image
        , new Point(rect.x, rect.y)
        , new Point(rect.x + rect.width, rect.y + rect.height)
        , new Scalar(0, 255, 0, 0)
      )
    })

    val filename = "faceDetecion.png"
    println(String.format("wtiting %s", filename))
    Imgcodecs.imwrite(filename, image)
  }
}
