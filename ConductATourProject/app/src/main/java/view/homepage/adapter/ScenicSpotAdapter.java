package view.homepage.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.conductatour.pack.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import model.ScenicSpotModel;

/**
 * 景点列表适配器
 */
public class ScenicSpotAdapter extends RecyclerView.Adapter<ScenicSpotAdapter.ViewHolder> {

    private List<ScenicSpotModel> datas = new ArrayList<>();

    public ScenicSpotAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adpter_scenic_spot_lists, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ScenicSpotModel scenicSpotModel = datas.get(position);
        holder.mScenicSpotName.setText(scenicSpotModel.getScenicName());
        holder.mScenicSpotDesc.setText(scenicSpotModel.getScenicDes());

        // 点击事件的初始化
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClickListener(datas.get(position), position);
            }
        });
    }

    public void flushDatas(List<ScenicSpotModel> datas) {
        this.datas.clear();
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.scenic_spot_name)
        TextView mScenicSpotName;

        @BindView(R.id.scenic_spot_desc)
        TextView mScenicSpotDesc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 条目点击事件
     */
    public interface OnItemClickListener {
        void onItemClickListener(ScenicSpotModel scenicSpotModel, int position);
    }
}
