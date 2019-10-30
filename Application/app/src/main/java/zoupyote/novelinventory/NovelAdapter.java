package zoupyote.novelinventory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class NovelAdapter extends BaseAdapter {
    private List<Novel> novels;
    private LayoutInflater inflater;

    public NovelAdapter(Context context, List<Novel> novels) {
        inflater = LayoutInflater.from(context);
        this.novels = novels;
    }

    public int getCount(){ return novels.size(); }

    public Object getItem(int position) { return novels.get(position); }

    public long getItemId(int position) { return position; }

    public View getView(int i, View convertView, ViewGroup parent){
        View result = inflater.inflate(R.layout.novel_layout,null);
        TextView novelName = result.findViewById(R.id.novelName);
        novelName.setText(novels.get(i).getName());
        TextView novelWriter = result.findViewById(R.id.novelWriter);
        novelWriter.setText(novels.get(i).getWriter());
        TextView novelScore = result.findViewById(R.id.novelScore);
        novelScore.setText(novels.get(i).getScoreString());
        return result;
    }


}
