package space.kovo.paster.activities.itemsActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import space.kovo.paster.R;
import space.kovo.paster.dtos.itemDto.ItemResponseDTO;

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    private final Context context;
    private final List<ItemResponseDTO> data;

    public ItemsAdapter(Context context, List<ItemResponseDTO> data) {
        this.context = context;
        this.data = data;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView itemPreview;
        private final LinearLayout itemPreviewWrapper;
        private final TextView itemIdentificator;

        public ViewHolder(View view) {
            super(view);
            itemPreview = view.findViewById(R.id.itemPreview);
            itemIdentificator = view.findViewById(R.id.itemIdentificator);
            itemPreviewWrapper = view.findViewById(R.id.itemPreviewWrapper);
            itemPreviewWrapper.setOnClickListener(ItemsAdapterListeners.copyToClipboardListener());
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        ItemResponseDTO dto = data.get(position);
        viewHolder.itemPreview.setText(dto.getPreview());
        viewHolder.itemIdentificator.setText(String.valueOf(dto.getId()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}