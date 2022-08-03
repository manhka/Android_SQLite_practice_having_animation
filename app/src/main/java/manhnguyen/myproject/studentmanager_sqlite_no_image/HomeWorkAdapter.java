package manhnguyen.myproject.studentmanager_sqlite_no_image;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class HomeWorkAdapter extends BaseAdapter {

    private MainActivity context;
    private int layout;
    private List<Homework> homeworkList;

    public HomeWorkAdapter(MainActivity context, int layout, List<Homework> homeworkList) {
        this.context = context;
        this.layout = layout;
        this.homeworkList = homeworkList;
    }

    @Override
    public int getCount() {
        return homeworkList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public class ViewHolder {
        TextView name;
        ImageView btnEdit, btnDelete;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = new ViewHolder();
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(layout, null);
            holder.name = (TextView) view.findViewById(R.id.textViewName);
            holder.btnEdit = (ImageView) view.findViewById(R.id.imageViewEdit);
            holder.btnDelete = (ImageView) view.findViewById(R.id.imageViewDelete);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // make values
        Homework homework = homeworkList.get(i);
        holder.name.setText(homework.getName());
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.DialogUpdateHomework(homework.getName(), homework.getId());
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.DeleteHomeWork(homework.getId(), homework.getName());
            }
        });
        // animation
        Animation animation= AnimationUtils.loadAnimation(context,R.anim.animation);
        view.startAnimation(animation);
        return view;
    }
}
