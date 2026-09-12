package org.telegram.messenger;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.Cells.HeaderCell;
import org.telegram.ui.Cells.TextCheckCell;
import org.telegram.ui.Cells.TextSettingsCell;
import org.telegram.ui.Components.RecyclerListView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AureliaSettingsActivity extends BaseFragment {

    private ListAdapter listAdapter;
    private RecyclerListView listView;

    private int antiRevokeRow;
    private int downloadBoostRow;
    private int joinUsRow;
    private int headerRow;

    @Override
    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        updateRows();
        return true;
    }

    private void updateRows() {
        antiRevokeRow = 0;
        downloadBoostRow = 1;
        joinUsRow = 2;
        headerRow = 3;
    }

    @Override
    public View createView(Context context) {
        actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        actionBar.setAllowOverlayTitle(true);
        actionBar.setTitle("Aurelia功能");
        actionBar.setActionBarMenuOnItemClick(new ActionBar.ActionBarMenuOnItemClick() {
            @Override
            public void onItemClick(int id) {
                if (id == -1) {
                    finishFragment();
                }
            }
        });

        listAdapter = new ListAdapter(context);

        fragmentView = new FrameLayout(context);
        FrameLayout frameLayout = (FrameLayout) fragmentView;

        listView = new RecyclerListView(context);
        listView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener((view, position) -> {
            if (position == antiRevokeRow) {
                AureliaSettings.getInstance().setAntiRevokeEnabled(!AureliaSettings.getInstance().isAntiRevokeEnabled());
                if (view instanceof TextCheckCell) {
                    ((TextCheckCell) view).setChecked(AureliaSettings.getInstance().isAntiRevokeEnabled());
                }
            } else if (position == downloadBoostRow) {
                AureliaSettings.getInstance().setDownloadBoostEnabled(!AureliaSettings.getInstance().isDownloadBoostEnabled());
                if (view instanceof TextCheckCell) {
                    ((TextCheckCell) view).setChecked(AureliaSettings.getInstance().isDownloadBoostEnabled());
                }
            } else if (position == joinUsRow) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/Aurelia5200"));
                    intent.setPackage("com.aurelia.app");
                    startActivity(intent);
                } catch (Exception e) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/Aurelia5200"));
                    startActivity(intent);
                }
            }
        });
        frameLayout.addView(listView, LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));

        return fragmentView;
    }

    private class ListAdapter extends RecyclerListView.SelectionAdapter {
        private Context mContext;

        public ListAdapter(Context context) {
            mContext = context;
        }

        @Override
        public int getItemCount() {
            return 4;
        }

        @Override
        public boolean isEnabled(int position) {
            return position != headerRow;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            switch (viewType) {
                case 0:
                    view = new TextCheckCell(mContext);
                    view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
                case 1:
                    view = new TextSettingsCell(mContext);
                    view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
                case 2:
                default:
                    view = new HeaderCell(mContext);
                    view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
            }
            return new RecyclerListView.Holder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            switch (holder.getItemViewType()) {
                case 0: {
                    TextCheckCell cell = (TextCheckCell) holder.itemView;
                    if (position == antiRevokeRow) {
                        cell.setText("防撤回", "别人撤回的消息仍然可见", AureliaSettings.getInstance().isAntiRevokeEnabled(), false);
                    } else if (position == downloadBoostRow) {
                        cell.setText("下载加速", "无需Telegram会员，下载速度由网速决定", AureliaSettings.getInstance().isDownloadBoostEnabled(), false);
                    }
                    break;
                }
                case 1: {
                    TextSettingsCell cell = (TextSettingsCell) holder.itemView;
                    if (position == joinUsRow) {
                        cell.setText("加入我们", false);
                    }
                    break;
                }
                case 2: {
                    HeaderCell cell = (HeaderCell) holder.itemView;
                    if (position == headerRow) {
                        cell.setText("关于");
                    }
                    break;
                }
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (position == antiRevokeRow || position == downloadBoostRow) {
                return 0;
            } else if (position == joinUsRow) {
                return 1;
            } else {
                return 2;
            }
        }
    }
}
