package zoupyote.novelinventory;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Novel implements Parcelable {

    private String name;
    private String writer;
    private String comment;
    public enum Score{Mauvais, Bof, PasMal, Bien, TB, CoupCoeur}
    private Score score;

    protected Novel(Parcel in) {
        name = in.readString();
        writer = in.readString();
        comment = in.readString();
    }

    public static final Creator<zoupyote.novelinventory.Novel> CREATOR = new Creator<zoupyote.novelinventory.Novel>() {
        @Override
        public zoupyote.novelinventory.Novel createFromParcel(Parcel in) {
            return new zoupyote.novelinventory.Novel(in);
        }

        @Override
        public zoupyote.novelinventory.Novel[] newArray(int size) {
            return new zoupyote.novelinventory.Novel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(writer);
    }




    public Novel(String name,String writer,String score, String comment){
        this.name = name;
        this.writer = writer;
        this.comment = comment;
        switch(score){
            case "Mauvais" : this.score = Score.Mauvais; break;
            case "Bof" : this.score = Score.Bof; break;
            case "Pas mal" : this.score = Score.PasMal; break;
            case "Bien" : this.score = Score.Bien; break;
            case "Très bien" : this.score = Score.TB; break;
            case "Coup de cœur" : this.score = Score.CoupCoeur; break;
            default : Log.i("Novel","Error in the score creation");
        }
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String commentaire) {
        this.comment = commentaire;
    }

    public String getName() {
        return name;
    }

    public String getWriter() {
        return writer;
    }

    public Score getScore() {
        return score;
    }

    public String getScoreString() {
        switch(score){
            case Mauvais:  return "Mauvais";
            case Bof:  return "Bof";
            case PasMal:  return "Pas mal";
            case Bien:  return "Bien";
            case TB:  return "Très bien";
            case CoupCoeur:  return "Coup de cœur";
            default : return null ;
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public void setScore(String score) {
        switch(score){
            case "Mauvais" : this.score = Score.Mauvais; break;
            case "Bof" : this.score = Score.Bof; break;
            case "Pas mal" : this.score = Score.PasMal; break;
            case "Bien" : this.score = Score.Bien; break;
            case "Très bien" : this.score = Score.TB; break;
            case "Coup de cœur" : this.score = Score.CoupCoeur; break;
            default : Log.e("Novel","Error in the score modification");
        }
    }

}

