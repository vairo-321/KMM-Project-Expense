import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Apps
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.TitleTopBarTypes
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.rememberNavigator
import navigation.Navigation
import org.koin.compose.KoinContext
import org.koin.core.context.KoinContext


@Composable
fun App() {
    PreComposeApp {
        KoinContext {
            val colors = getColorsTheme()

            AppTheme {

                val navigator = rememberNavigator()
                val titleTopBar = getTitleTopBar(navigator)
                val isEditOrAddExpense = titleTopBar != TitleTopBarTypes.DASHBOARD.value

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    text = titleTopBar,
                                    color = colors.textColor,
                                    fontSize = 25.sp
                                )
                            },
                            elevation = 0.dp,
                            navigationIcon = {
                                if (isEditOrAddExpense) {
                                    IconButton(
                                        onClick = {
                                            navigator.popBackStack()
                                        }
                                    )
                                    {
                                        Icon(
                                            modifier = Modifier.padding(start = 16.dp),
                                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                            contentDescription = "back arrow Icon",
                                            tint = colors.textColor
                                        )
                                    }
                                } else {
                                    Icon(
                                        modifier = Modifier.padding(start = 16.dp),
                                        imageVector = Icons.Default.Apps,
                                        contentDescription = "Dashboard Icon",
                                        tint = colors.textColor
                                    )
                                }

                            },
                            backgroundColor = colors.backgroundColor
                        )
                    },
                    floatingActionButton = {
                        if (!isEditOrAddExpense) {
                            FloatingActionButton(
                                modifier = Modifier.padding(16.dp),
                                onClick = {
                                    navigator.navigate("/addExpenses")
                                },
                                shape = RoundedCornerShape(50),
                                backgroundColor = colors.addIconColor,
                                contentColor = Color.White
                            )
                            {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Add Icon",
                                    tint = Color.White
                                )
                            }
                        }
                    }
                )
                {
                    Navigation(navigator)
                }
            }
        }
    }
}

@Composable
fun getTitleTopBar(navigator: Navigator): String {
    var titleTopBar = TitleTopBarTypes.DASHBOARD

    val isOnAddExpense = navigator.currentEntry.collectAsState(null).value?.route?.route.equals("/addExpenses/{id}?")
    if (isOnAddExpense){
        titleTopBar = TitleTopBarTypes.ADD
    }

    val isOnEditExpense = navigator.currentEntry.collectAsState(null).value?.path<Long>("id")
    isOnEditExpense?.let {
        titleTopBar = TitleTopBarTypes.EDIT
    }

    return titleTopBar.value

}

