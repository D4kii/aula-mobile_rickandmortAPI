package br.senai.sp.jandira.rickandmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.rickandmorty.model.Character
import br.senai.sp.jandira.rickandmorty.model.CharacterList
import br.senai.sp.jandira.rickandmorty.model.Info
import br.senai.sp.jandira.rickandmorty.service.RetrofitFactory
import br.senai.sp.jandira.rickandmorty.ui.theme.RickAndMortyTheme
import coil.compose.AsyncImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.compose.runtime.remember as remember

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RickAndMortyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {

    val listCharacter = remember {
        mutableStateOf(listOf<br.senai.sp.jandira.rickandmorty.model.Character>())
    }
    val info by remember {
        mutableStateOf(Info())
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Button(onClick = {
            //Cria uma chamada para o endpoint
            val call = RetrofitFactory().getCharacterService().getCharacter()

            //Executar a chamada
            call.enqueue(object : Callback<CharacterList>{
                override fun onResponse(
                    call: Call<CharacterList>,
                    response: Response<CharacterList>
                ) {
                    listCharacter.value = response.body()!!.results
                    val teste = response.body()!!.info
                }

                override fun onFailure(call: Call<CharacterList>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })

        }) {
            Text(
                text = "Listar Personagens")
        }
        Row() {
            Text(
                text = "Count",
            modifier = Modifier.size(width = 60.dp, height = 20.dp)
            )
            Text(
                text = "400",
                modifier = Modifier.size(width = 60.dp, height = 20.dp),
                textAlign = TextAlign.End
            )
        }
        Row() {
            Text(
                text = "Pages",
                modifier = Modifier.size(width = 60.dp, height = 20.dp)
            )
            Text(
                text = "${info.pages}",
                modifier = Modifier.size(width = 60.dp, height = 20.dp),
                textAlign = TextAlign.End
            )
        }

        LazyColumn(){
            items(listCharacter.value){
                Card(
                    backgroundColor = Color.Magenta,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                ) {

                    Row() {
                        AsyncImage(
                            model = it.image,
                            contentDescription = "Character avatar")
                    }
                    Column(
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text(text = it.name,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(text = it.species)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    RickAndMortyTheme {
        Greeting("Android")
    }
}